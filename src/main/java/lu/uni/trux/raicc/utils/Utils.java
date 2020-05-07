package lu.uni.trux.raicc.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lu.uni.trux.raicc.exceptions.MethodNotFoundException;
import lu.uni.trux.raicc.extractors.WrapperLocalExtractorImpl;
import lu.uni.trux.raicc.extractors.WrapperLocalExtractorParam0;
import lu.uni.trux.raicc.extractors.WrapperLocalExtractorParam1;
import lu.uni.trux.raicc.extractors.WrapperLocalExtractorParam2;
import lu.uni.trux.raicc.extractors.WrapperLocalExtractorParam3;
import lu.uni.trux.raicc.extractors.WrapperLocalExtractorParam4;
import soot.Body;
import soot.Local;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Stmt;

public class Utils {

	protected static Logger logger = LoggerFactory.getLogger(Utils.class);

	public static List<Local> getLocalsUsedToConstructIntentWrapper(Unit u) {
		Stmt stmt = (Stmt) u;
		List<Local> intents = new ArrayList<Local>();
		if(stmt.containsInvokeExpr()) {
			InvokeExpr invExpr = stmt.getInvokeExpr();
			SootMethod m = invExpr.getMethod();
			WrapperLocalExtractorImpl wle = new WrapperLocalExtractorParam0(null);
			wle = new WrapperLocalExtractorParam1(wle);
			wle = new WrapperLocalExtractorParam2(wle);
			wle = new WrapperLocalExtractorParam3(wle);
			wle = new WrapperLocalExtractorParam4(wle);
			Value v = null;
			boolean b = false,
					p = false;
			try {
				b = IndirectMethodChecker.v().isWrapperBase(m);
				p = IndirectMethodChecker.v().isWrapperParameter(m);
			} catch (MethodNotFoundException e) {
				logger.error(e.getMessage());
			}

			if(b) {
				v = ((InstanceInvokeExpr)invExpr).getBase();
			} else if(p) {
				v = wle.extractWrapperLocal(invExpr);
			}
			intents = extractIntents(v); 
		}
		return intents;
	}
	
	private static List<Local> extractIntents(Value v){
		List<Local> intents = new ArrayList<Local>();
		Local potentialIntent = null;
		for(SootClass sc : Scene.v().getApplicationClasses()) {
			for(SootMethod sm : sc.getMethods()) {
				if(sm.hasActiveBody()) {
					Body body = sm.retrieveActiveBody();
					if(body.getLocals().contains(v)) {
						for(Unit u : body.getUnits()) {
							if(u instanceof AssignStmt) {
								AssignStmt ass = (AssignStmt)u;
								Value leftOp = ass.getLeftOp();
								if(leftOp.equals(v)) {
									Value rightOp = ass.getRightOp();
									if(rightOp instanceof InvokeExpr) {
										InvokeExpr rightOpInvExpr = (InvokeExpr)rightOp;
										SootMethod rightOpInvExprMethod = rightOpInvExpr.getMethod();
										if(isPendingIntentCreationMethod(rightOpInvExprMethod)) {
											potentialIntent = (Local)rightOpInvExpr.getArg(2);
											intents.add(potentialIntent);
										} else if(isIntentSenderCreationMethod(rightOpInvExprMethod)) {
											intents.addAll(extractIntents(((InstanceInvokeExpr)rightOpInvExpr).getBase()));
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

	private static boolean isIntentSenderCreationMethod(SootMethod m) {
		return m.getSignature().equals(Constants.ANDROID_APP_PENDING_INTENT_GET_INTENTSENDER);
	}

	private static boolean isPendingIntentCreationMethod(SootMethod m) {
		String sig = m.getSignature();
		if(sig.equals(Constants.ANDROID_APP_PENDING_INTENT_GET_ACTIVITY) ||
				sig.equals(Constants.ANDROID_APP_PENDING_INTENT_GET_BROADCAST) ||
				sig.equals(Constants.ANDROID_APP_PENDING_INTENT_GET_SERVICE)) {
			return true;
		}
		return false;
	}

}
