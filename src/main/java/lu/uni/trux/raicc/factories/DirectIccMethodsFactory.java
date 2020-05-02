package lu.uni.trux.raicc.factories;

import java.util.ArrayList;
import java.util.List;

import lu.uni.trux.raicc.utils.Constants;
import soot.Body;
import soot.Local;
import soot.Scene;
import soot.SootMethod;
import soot.SootMethodRef;
import soot.Unit;
import soot.Value;
import soot.jimple.IntConstant;
import soot.jimple.Jimple;

public class DirectIccMethodsFactory {

	private static DirectIccMethodsFactory instance;

	private DirectIccMethodsFactory() {}

	public static DirectIccMethodsFactory v() {
		if(instance == null) {
			instance = new DirectIccMethodsFactory();
		}
		return instance;
	}

	private List<Unit> generateGenericInvokeStmt(SootMethod m, Local intent ,String methodSig, boolean forResult){
		List<Unit> unitsToAdd = new ArrayList<Unit>();
		if(m.hasActiveBody()) {
			Body b = m.retrieveActiveBody();
			Local thisLocal = b.getThisLocal();
			SootMethodRef methodRef= this.getMethodRef(forResult ? Constants.ANDROID_APP_ACTIVITY:Constants.ANDROID_CONTENT_CONTEXT, methodSig);
			List<Value> params = new ArrayList<Value>();
			params.add(intent);
			if(forResult) {
				params.add(IntConstant.v(0));
			}
			unitsToAdd.add(Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(thisLocal, methodRef, params)));
		}
		return unitsToAdd;
	}

	private SootMethodRef getMethodRef(String className, String methodName) {
		return Scene.v().getSootClass(className).getMethod(methodName).makeRef();
	}

	public List<Unit> generateStartActivity(SootMethod m, Local intent){
		return this.generateGenericInvokeStmt(m, intent, Constants.START_ACTIVITY, false);
	}

	public List<Unit> generateStartService(SootMethod m, Local intent){
		return this.generateGenericInvokeStmt(m, intent, Constants.START_SERVICE, false);
	}

	public List<Unit> generateSendBroadcast(SootMethod m, Local intent){
		return this.generateGenericInvokeStmt(m, intent, Constants.SEND_BROADCAST, false);
	}

	public List<Unit> generateStartActivityForResult(SootMethod m, Local intent){
		return this.generateGenericInvokeStmt(m, intent, Constants.START_ACTIVITY_FOR_RESULT, true);
	}
}