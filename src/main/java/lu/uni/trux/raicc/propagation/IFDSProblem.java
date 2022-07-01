package lu.uni.trux.raicc.propagation;


import heros.DefaultSeeds;
import heros.FlowFunction;
import heros.FlowFunctions;
import heros.InterproceduralCFG;
import heros.flowfunc.Identity;
import heros.flowfunc.KillAll;
import lu.uni.trux.raicc.utils.Constants;
import lu.uni.trux.raicc.utils.Utils;
import soot.*;
import soot.jimple.*;
import soot.jimple.toolkits.ide.DefaultJimpleIFDSTabulationProblem;
import soot.jimple.toolkits.ide.JimpleIFDSSolver;
import soot.toolkits.scalar.Pair;

import java.util.*;

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
public class IFDSProblem extends DefaultJimpleIFDSTabulationProblem<Pair<Value, Pair<Set<String>, Set<Value>>>, InterproceduralCFG<Unit, SootMethod>> {

    private final JimpleIFDSSolver<?, InterproceduralCFG<Unit, SootMethod>> solver;

    public IFDSProblem(InterproceduralCFG<Unit, SootMethod> icfg) {
        super(icfg);
        this.solver = new JimpleIFDSSolver<>(this);
    }

    @Override
    protected FlowFunctions<Unit, Pair<Value, Pair<Set<String>, Set<Value>>>, SootMethod> createFlowFunctionsFactory() {
        return new FlowFunctions<Unit, Pair<Value, Pair<Set<String>, Set<Value>>>, SootMethod>() {

            public FlowFunction<Pair<Value, Pair<Set<String>, Set<Value>>>> getNormalFlowFunction(Unit src, Unit dest) {
                if (src instanceof DefinitionStmt) {
                    DefinitionStmt defnStmt = (DefinitionStmt) src;
                    final Value rightOp = defnStmt.getRightOp();
                    final Value leftOp = defnStmt.getLeftOp();
                    if (rightOp instanceof Ref && rightOp instanceof Local) {
                        return new FlowFunction<Pair<Value, Pair<Set<String>, Set<Value>>>>() {
                            public Set<Pair<Value, Pair<Set<String>, Set<Value>>>> computeTargets(Pair<Value, Pair<Set<String>, Set<Value>>> source) {
                                if (source.getO1().equivTo(leftOp)) {
                                    return Collections.emptySet();
                                } else if (rightOp.equivTo(source.getO1())) {
                                    Set<Pair<Value, Pair<Set<String>, Set<Value>>>> res = new LinkedHashSet<>();
                                    res.add(new Pair<>(leftOp, source.getO2()));
                                    res.add(source);
                                    return res;
                                } else {
                                    return Collections.singleton(source);
                                }
                            }
                        };
                    } else {
                        return Identity.v();
                    }
                }
                return Identity.v();
            }

            public FlowFunction<Pair<Value, Pair<Set<String>, Set<Value>>>> getCallFlowFunction(final Unit src, final SootMethod dest) {
                Stmt stmt = (Stmt) src;
                InvokeExpr ie = stmt.getInvokeExpr();
                final List<Value> callArgs = ie.getArgs();
                final List<Local> paramLocals = new ArrayList<>();
                for (int i = 0; i < dest.getParameterCount(); i++) {
                    paramLocals.add(dest.getActiveBody().getParameterLocal(i));
                }
                return new FlowFunction<Pair<Value, Pair<Set<String>, Set<Value>>>>() {
                    public Set<Pair<Value, Pair<Set<String>, Set<Value>>>> computeTargets(Pair<Value, Pair<Set<String>, Set<Value>>> source) {
                        if (!dest.getName().equals("<clinit>") && !dest.getSubSignature().equals("void run()")) {
                            Value value = source.getO1();
                            int argIndex = callArgs.indexOf(value);
                            if (argIndex > -1 && paramLocals.size() > argIndex) {
                                return Collections.singleton(new Pair<>(paramLocals.get(argIndex), source.getO2()));
                            }
                        }
                        return Collections.emptySet();
                    }
                };
            }

            public FlowFunction<Pair<Value, Pair<Set<String>, Set<Value>>>> getReturnFlowFunction(Unit callSite, SootMethod callee, Unit exitStmt, Unit retSite) {
                if (exitStmt instanceof ReturnStmt) {
                    ReturnStmt returnStmt = (ReturnStmt) exitStmt;
                    Value op = returnStmt.getOp();
                    if (op instanceof Local) {
                        if (callSite instanceof DefinitionStmt) {
                            DefinitionStmt defnStmt = (DefinitionStmt) callSite;
                            Value leftOp = defnStmt.getLeftOp();
                            if (leftOp instanceof Local) {
                                final Local tgtLocal = (Local) leftOp;
                                final Local retLocal = (Local) op;
                                return new FlowFunction<Pair<Value, Pair<Set<String>, Set<Value>>>>() {
                                    public Set<Pair<Value, Pair<Set<String>, Set<Value>>>> computeTargets(Pair<Value, Pair<Set<String>, Set<Value>>> source) {
                                        if (source.getO1() == retLocal) {
                                            return Collections.singleton(new Pair<>(tgtLocal, source.getO2()));
                                        }
                                        return Collections.emptySet();
                                    }
                                };
                            }
                        }
                    }
                }
                return KillAll.v();
            }

            public FlowFunction<Pair<Value, Pair<Set<String>, Set<Value>>>> getCallToReturnFlowFunction(Unit call, Unit returnSite) {
                if (call instanceof DefinitionStmt) {
                    DefinitionStmt defnStmt = (DefinitionStmt) call;
                    final Value leftOp = defnStmt.getLeftOp();
                    if (defnStmt.containsInvokeExpr()) {
                        InvokeExpr ie = defnStmt.getInvokeExpr();
                        SootMethod callee = ie.getMethod();
                        String calleSig = callee.getSignature();
                        if (Utils.isPendingIntentCreationMethod(callee)) {
                            return new FlowFunction<Pair<Value, Pair<Set<String>, Set<Value>>>>() {
                                public Set<Pair<Value, Pair<Set<String>, Set<Value>>>> computeTargets(Pair<Value, Pair<Set<String>, Set<Value>>> source) {
                                    if (source == zeroValue()) {
                                        Set<Pair<Value, Pair<Set<String>, Set<Value>>>> res = new LinkedHashSet<>();
                                        Set<String> types = new HashSet<>();
                                        String type = null;
                                        switch (calleSig) {
                                            case Constants.ANDROID_APP_PENDING_INTENT_GET_ACTIVITY:
                                            case Constants.ANDROID_APP_PENDING_INTENT_GET_ACTIVITY_BUNDLE:
                                                type = Constants.ACTIVITY;
                                                break;
                                            case Constants.ANDROID_APP_PENDING_INTENT_GET_SERVICE:
                                                type = Constants.SERVICE;
                                                break;
                                            case Constants.ANDROID_APP_PENDING_INTENT_GET_BROADCAST:
                                                type = Constants.BROADCASTRECEIVER;
                                                break;
                                        }
                                        types.add(type);
                                        Set<Value> intents = new HashSet<>();
                                        intents.add(ie.getArg(2));
                                        Pair<Value, Pair<Set<String>, Set<Value>>> p = new Pair<>(leftOp, new Pair<>(types, intents));
                                        res.add(p);
                                        res.add(zeroValue());
                                        return res;
                                    } else if (source.getO1().equivTo(leftOp)) {
                                        return Collections.emptySet();
                                    } else {
                                        return Collections.singleton(source);
                                    }
                                }
                            };
                        }
                    } else {
                        return Identity.v();
                    }
                }
                return Identity.v();
            }
        };
    }

    @Override
    protected Pair<Value, Pair<Set<String>, Set<Value>>> createZeroValue() {
        return new Pair<>(Jimple.v().newLocal("<dummy>", UnknownType.v()), new Pair<>(new HashSet<>(), new HashSet<>()));
    }

    public void solve() {
        this.solver.solve();
    }

    @Override
    public Map<Unit, Set<Pair<Value, Pair<Set<String>, Set<Value>>>>> initialSeeds() {
        return DefaultSeeds.make(Collections.singleton(Scene.v().getEntryPoints().get(0).getActiveBody().getUnits().getFirst()), zeroValue());
    }

    public Set<Pair<Set<String>, Set<Value>>> getResults(Value v, Unit u) {
        Set<Pair<Set<String>, Set<Value>>> results = new HashSet<>();
        Set<Pair<Value, Pair<Set<String>, Set<Value>>>> resultComputed = (Set<Pair<Value, Pair<Set<String>, Set<Value>>>>) this.solver.ifdsResultsAt(u);
        for (Pair<Value, Pair<Set<String>, Set<Value>>> pair : resultComputed) {
            if (pair.getO1().equals(v)) {
                results.add(pair.getO2());
            }
        }
        return results;
    }
}