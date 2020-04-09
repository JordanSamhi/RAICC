package lu.uni.trux.indirecticcresolver.factories;

import java.util.ArrayList;
import java.util.List;

import lu.uni.trux.indirecticcresolver.utils.Constants;
import soot.Body;
import soot.Local;
import soot.Scene;
import soot.SootMethod;
import soot.SootMethodRef;
import soot.Unit;
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

	private List<Unit> generateGenericInvokeStmt(SootMethod m, Local intent ,String methodSig){
		List<Unit> unitsToAdd = new ArrayList<Unit>();
		if(m.hasActiveBody()) {
			Body b = m.retrieveActiveBody();
			Local thisLocal = b.getThisLocal();
			SootMethodRef methodRef= this.getMethodRef(Constants.ANDROID_CONTENT_CONTEXT, methodSig);
			unitsToAdd.add(Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(thisLocal, methodRef, intent)));
		}
		return unitsToAdd;
	}

	private SootMethodRef getMethodRef(String className, String methodName) {
		return Scene.v().getSootClass(className).getMethod(methodName).makeRef();
	}

	public List<Unit> generateStartActivity(SootMethod m, Local intent){
		return this.generateGenericInvokeStmt(m, intent, Constants.START_ACTIVITY);
	}

	public List<Unit> generateStartService(SootMethod m, Local intent){
		return this.generateGenericInvokeStmt(m, intent, Constants.START_SERVICE);
	}

	public List<Unit> generateSendBroadcast(SootMethod m, Local intent){
		return this.generateGenericInvokeStmt(m, intent, Constants.SEND_BROADCAST);
	}
}