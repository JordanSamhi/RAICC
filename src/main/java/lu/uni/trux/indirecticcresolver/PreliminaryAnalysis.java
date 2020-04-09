package lu.uni.trux.indirecticcresolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.cse.siis.coal.AnalysisParameters;
import edu.psu.cse.siis.coal.FatalAnalysisException;
import edu.psu.cse.siis.coal.Result;
import edu.psu.cse.siis.coal.Results;
import edu.psu.cse.siis.coal.field.values.FieldValue;
import edu.psu.cse.siis.coal.values.PropagationValue;
import edu.psu.cse.siis.ic3.Ic3Analysis;
import edu.psu.cse.siis.ic3.Ic3CommandLineArguments;
import lu.uni.trux.indirecticcresolver.extractors.PILEAndroidAppAlarmManagerSet;
import lu.uni.trux.indirecticcresolver.extractors.PendingIntentLocalExtractorImpl;
import lu.uni.trux.indirecticcresolver.factories.DirectIccMethodsFactory;
import lu.uni.trux.indirecticcresolver.utils.Constants;
import soot.Body;
import soot.Local;
import soot.PackManager;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.options.Options;

public class PreliminaryAnalysis extends Ic3Analysis {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void processResults(Ic3CommandLineArguments commandLineArguments)
			throws FatalAnalysisException {
		for (Result result : Results.getResults()) {
			for(Entry<Unit, Map<Integer, Object>> entry1 : result.getResults().entrySet()) {
				Unit stmt = entry1.getKey();
				SootMethod stmtMethod = AnalysisParameters.v().getIcfg().getMethodOf(stmt);
				for(Entry<Integer, Object> entry2 : entry1.getValue().entrySet()) {
					Object o = entry2.getValue();
					if(o instanceof PropagationValue) {
						PropagationValue pv = (PropagationValue) o;
						Set<FieldValue> fValues = pv.getValuesForField(Constants.TARGET_TYPE);
						if(fValues != null && !fValues.isEmpty()) {
							if(this.isIndirectIccMethodCall(stmt)) {
								List<Local> intents = this.getLocalsUsedToConstructPendingIntent(stmt);
								if(intents != null && !intents.isEmpty()) {
									for(Local intent : intents) {
										List<Unit> unitsToAdd = null;
										Object v = null;
										for(FieldValue fv : fValues) {
											v = fv.getValue();
											if(v.equals(Constants.ACTIVITY)) {
												logger.info("Adding startACtivity statement.");
												unitsToAdd = DirectIccMethodsFactory.v().generateStartActivity(stmtMethod, intent);
											}else if(v.equals(Constants.RECEIVER)) {
												logger.info("Adding sendBroadcast statement.");
												unitsToAdd = DirectIccMethodsFactory.v().generateSendBroadcast(stmtMethod, intent);
											}else if(v.equals(Constants.SERVICE)) {
												logger.info("Adding startService statement.");
												unitsToAdd = DirectIccMethodsFactory.v().generateStartService(stmtMethod, intent);
											}
											if(unitsToAdd != null && !unitsToAdd.isEmpty()) {
												Body b = stmtMethod.retrieveActiveBody();
												b.getUnits().insertAfter(unitsToAdd, stmt);
												b.validate();
												Options.v().set_output_format(Options.output_format_dex);
												// TODO dynamic tmp directory
												Options.v().set_output_dir("/tmp/");
												Options.v().set_force_overwrite(true);
												PackManager.v().writeOutput();
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private List<Local> getLocalsUsedToConstructPendingIntent(Unit stmt) {
		List<Local> intents = new ArrayList<Local>();
		if(stmt instanceof InvokeStmt) {
			InvokeExpr invExpr = ((InvokeStmt)stmt).getInvokeExpr();
			PendingIntentLocalExtractorImpl pile = new PILEAndroidAppAlarmManagerSet(null);
			Value pi = pile.extractPendingIntentLocal(invExpr);
			for(SootClass sc : Scene.v().getApplicationClasses()) {
				for(SootMethod sm : sc.getMethods()) {
					if(sm.hasActiveBody()) {
						Body b = sm.retrieveActiveBody();
						if(b.getLocals().contains(pi)) {
							for(Unit u : b.getUnits()) {
								if(u instanceof AssignStmt) {
									AssignStmt ass = (AssignStmt)u;
									Value leftOp = ass.getLeftOp();
									if(leftOp.equals(pi)) {
										Value rightOp = ass.getRightOp();
										if(rightOp instanceof InvokeExpr) {
											InvokeExpr rightOpInvExpr = (InvokeExpr)rightOp;
											SootMethod rightOpInvExprMethod = rightOpInvExpr.getMethod();
											if(this.isPendingIntentCreationMethod(rightOpInvExprMethod)) {
												intents.add((Local)rightOpInvExpr.getArg(2));
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return intents;
	}

	private boolean isPendingIntentCreationMethod(SootMethod m) {
		String sig = m.getSignature();
		if(sig.equals(Constants.ANDROID_APP_PENDING_INTENT_GET_ACTIVITY) ||
				sig.equals(Constants.ANDROID_APP_PENDING_INTENT_GET_BROADCAST) ||
				sig.equals(Constants.ANDROID_APP_PENDING_INTENT_GET_SERVICE)) {
			return true;
		}
		return false;
	}

	private boolean isIndirectIccMethodCall(Unit stmt) {
		if(stmt instanceof InvokeStmt) {
			InvokeExpr invExpr = ((InvokeStmt)stmt).getInvokeExpr();
			SootMethod method = invExpr.getMethod();
			InputStream fis = null;
			BufferedReader br = null;
			String line = null;
			try {
				fis = this.getClass().getResourceAsStream(Constants.INDIRECT_ICC_METHODS);
				br = new BufferedReader(new InputStreamReader(fis));
				while ((line = br.readLine()) != null)   {
					if(method.getSignature().equals(line)) {
						br.close();
						fis.close();
						return true;
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			try {
				br.close();
				fis.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return false;
	}

}
