package lu.uni.trux.raicc.patch;

import lu.uni.trux.raicc.factories.AtypicalIccMethodsFactory;
import lu.uni.trux.raicc.libs.LibrariesManager;
import lu.uni.trux.raicc.propagation.IFDSProblem;
import lu.uni.trux.raicc.utils.AtypicalMethodChecker;
import lu.uni.trux.raicc.utils.Constants;
import lu.uni.trux.raicc.utils.Utils;
import lu.uni.trux.raicc.utils.Writer;
import soot.*;
import soot.jimple.InvokeExpr;
import soot.jimple.Stmt;
import soot.toolkits.scalar.Pair;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class Instrumenter {

    private static Instrumenter instance;

    public static Instrumenter v() {
        if (instance == null) {
            instance = new Instrumenter();
        }
        return instance;
    }

    private Instrumenter() {
    }

    public void instrument(IFDSProblem problem) {
        Stmt stmt;
        InvokeExpr ie;
        Map<Unit, List<Unit>> targetToToInsert;
        for (SootClass sc : Scene.v().getApplicationClasses()) {
            if (!Utils.isSystemClass(sc) && !LibrariesManager.v().isLibrary(sc)) {
                for (SootMethod sm : sc.getMethods()) {
                    if (sm.isConcrete()) {
                        Body b = sm.retrieveActiveBody();
                        targetToToInsert = new HashMap<>();
                        for (Unit u : b.getUnits()) {
                            stmt = (Stmt) u;
                            if (stmt.containsInvokeExpr()) {
                                ie = stmt.getInvokeExpr();
                                if (AtypicalMethodChecker.v().isAtypicalMethod(ie.getMethod())) {
                                    Value v = Utils.getIntentWrapper(ie);
                                    Set<Pair<Set<String>, Set<Value>>> result = problem.getResults(v, u);
                                    for (Pair<Set<String>, Set<Value>> p : result) {
                                        Set<String> componentTypes = p.getO1();
                                        Set<Value> intents = p.getO2();
                                        List<Unit> unitsToAdd = null;
                                        for (String componentType : componentTypes) {
                                            for (Value intent : intents) {
                                                if (intent instanceof Local) {
                                                    Local intentLocal = (Local) intent;
                                                    switch (componentType) {
                                                        case Constants.ACTIVITY:
                                                            if (AtypicalMethodChecker.v().isForResultMethod(sm)) {
                                                                Writer.v().pinfo("Adding startActivityForResult statement.");
                                                                unitsToAdd = AtypicalIccMethodsFactory.v().generateStartActivityForResult(sm, intentLocal);
                                                            } else {
                                                                Writer.v().pinfo("Adding startActivity statement.");
                                                                unitsToAdd = AtypicalIccMethodsFactory.v().generateStartActivity(sm, intentLocal);
                                                            }
                                                            break;
                                                        case Constants.BROADCASTRECEIVER:
                                                            Writer.v().pinfo("Adding sendBroadcast statement.");
                                                            unitsToAdd = AtypicalIccMethodsFactory.v().generateSendBroadcast(sm, intentLocal);
                                                            break;
                                                        case Constants.SERVICE:
                                                            Writer.v().pinfo("Adding startService statement.");
                                                            unitsToAdd = AtypicalIccMethodsFactory.v().generateStartService(sm, intentLocal);
                                                            break;
                                                    }
                                                    if (unitsToAdd != null && !unitsToAdd.isEmpty()) {
                                                        targetToToInsert.put(u, unitsToAdd);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        for (Map.Entry<Unit, List<Unit>> entry : targetToToInsert.entrySet()) {
                            b.getUnits().insertAfter(entry.getValue(), entry.getKey());
                            b.validate();
                        }
                    }
                }
            }
        }
    }
}