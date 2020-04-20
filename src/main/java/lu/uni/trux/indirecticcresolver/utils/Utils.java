package lu.uni.trux.indirecticcresolver.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lu.uni.trux.indirecticcresolver.exceptions.MethodNotFoundException;
import lu.uni.trux.indirecticcresolver.extractors.WAndroidAppAlarmManagerSet;
import lu.uni.trux.indirecticcresolver.extractors.WrapperLocalExtractorImpl;
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

public class Utils {

	protected static Logger logger = LoggerFactory.getLogger(Utils.class);

	public static List<Local> getLocalsUsedToConstructIntentWrapper(Unit stmt) {
		// considered different types of methods
		// parameter : pendingintent
		// paramter: intentsender
		// base : peindngtent
		// base intensender
		List<Local> intents = new ArrayList<Local>();
		if(stmt instanceof InvokeStmt) {
			InvokeExpr invExpr = ((InvokeStmt)stmt).getInvokeExpr();
			SootMethod m = invExpr.getMethod();
			WrapperLocalExtractorImpl pile = new WAndroidAppAlarmManagerSet(null);
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
				v = pile.extractWrapperLocal(invExpr);
			}
			intents = extractIntents(v); 
		}
		return intents;
	}
	
	private static List<Local> extractIntents(Value v){
		List<Local> intents = new ArrayList<Local>();
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
											intents.add((Local)rightOpInvExpr.getArg(2));
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
