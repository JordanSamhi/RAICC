package lu.uni.trux.raicc.factories;

/*-
 * #%L
 * RAICC
 * 
 * %%
 * Copyright (C) 2022 Jordan Samhi
 * University of Luxembourg - Interdisciplinary Centre for
 * Security Reliability and Trust (SnT) - TruX - All rights reserved
 *
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import lu.uni.trux.raicc.utils.Constants;
import soot.*;
import soot.jimple.IntConstant;
import soot.jimple.Jimple;

import java.util.ArrayList;
import java.util.List;

public class AtypicalIccMethodsFactory {

	private static AtypicalIccMethodsFactory instance;

	private AtypicalIccMethodsFactory() {}

	public static AtypicalIccMethodsFactory v() {
		if(instance == null) {
			instance = new AtypicalIccMethodsFactory();
		}
		return instance;
	}

	private List<Unit> generateGenericInvokeStmt(SootMethod m, Local intent , String methodSig, boolean forResult){
		List<Unit> unitsToAdd = new ArrayList<>();
		if(m.hasActiveBody()) {
			Body b = m.retrieveActiveBody();
			Local thisLocal = b.getThisLocal();
			SootMethodRef methodRef= this.getMethodRef(forResult ? Constants.ANDROID_APP_ACTIVITY:Constants.ANDROID_CONTENT_CONTEXT, methodSig);
			List<Value> params = new ArrayList<>();
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