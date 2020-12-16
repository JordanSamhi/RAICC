package lu.uni.trux.raicc;

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
import lu.uni.trux.raicc.exceptions.MethodNotFoundException;
import lu.uni.trux.raicc.factories.DirectIccMethodsFactory;
import lu.uni.trux.raicc.utils.Constants;
import lu.uni.trux.raicc.utils.IndirectMethodChecker;
import lu.uni.trux.raicc.utils.Utils;
import soot.Body;
import soot.Local;
import soot.PackManager;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.InvokeExpr;
import soot.jimple.Stmt;
import soot.options.Options;

public class PreliminaryAnalysis extends Ic3Analysis {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void processResults(Ic3CommandLineArguments commandLineArguments)
			throws FatalAnalysisException {
		for (Result result : Results.getResults()) {
			for(Entry<Unit, Map<Integer, Object>> entry1 : result.getResults().entrySet()) {
				Unit unit = entry1.getKey();
				Stmt stmt = (Stmt) unit;
				SootMethod stmtMethod = AnalysisParameters.v().getIcfg().getMethodOf(unit);
				if(stmt.containsInvokeExpr()) {
					InvokeExpr invExpr = stmt.getInvokeExpr();
					SootMethod methodCalled = invExpr.getMethod();
					for(Entry<Integer, Object> entry2 : entry1.getValue().entrySet()) {
						Object o = entry2.getValue();
						if(o instanceof PropagationValue) {
							PropagationValue pv = (PropagationValue) o;
							Set<FieldValue> fValues = pv.getValuesForField(Constants.TARGET_TYPE);
							if(fValues != null && !fValues.isEmpty()) {
								if(IndirectMethodChecker.v().isIndirectMethod(methodCalled)) {
									List<Local> intents = Utils.getLocalsUsedToConstructIntentWrapper(unit);
									if(intents != null && !intents.isEmpty()) {
										for(Local intent : intents) {
											List<Unit> unitsToAdd = null;
											Object v = null;
											for(FieldValue fv : fValues) {
												v = fv.getValue();
												if(v.equals(Constants.ACTIVITY)) {
													boolean isForResult = false;
													try {
														isForResult = IndirectMethodChecker.v().isForResultMethod(methodCalled);
													} catch (MethodNotFoundException e) {
														logger.error(e.getMessage());
													} 
													if(isForResult) {
														logger.info("Adding startActivityForResult statement.");
														unitsToAdd = DirectIccMethodsFactory.v().generateStartActivityForResult(stmtMethod, intent);
													}else {
														logger.info("Adding startActivity statement.");
														unitsToAdd = DirectIccMethodsFactory.v().generateStartActivity(stmtMethod, intent);
													}
												}else if(v.equals(Constants.RECEIVER)) {
													logger.info("Adding sendBroadcast statement.");
													unitsToAdd = DirectIccMethodsFactory.v().generateSendBroadcast(stmtMethod, intent);
												}else if(v.equals(Constants.SERVICE)) {
													logger.info("Adding startService statement.");
													unitsToAdd = DirectIccMethodsFactory.v().generateStartService(stmtMethod, intent);
												}
												if(unitsToAdd != null && !unitsToAdd.isEmpty()) {
													Body b = stmtMethod.retrieveActiveBody();
													b.getUnits().insertAfter(unitsToAdd, unit);
													b.validate();
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
		Options.v().set_output_format(Options.output_format_dex);
		Options.v().set_output_dir(Constants.TARGET_TMP_DIR);
		Options.v().set_force_overwrite(true);
		PackManager.v().writeOutput();
	}
}
