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
import lu.uni.trux.raicc.libs.LibrariesManager;
import lu.uni.trux.raicc.utils.Constants;
import lu.uni.trux.raicc.utils.IndirectMethodChecker;
import lu.uni.trux.raicc.utils.Utils;
import soot.Body;
import soot.Local;
import soot.PackManager;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.InvokeExpr;
import soot.jimple.Stmt;
import soot.options.Options;

/*-
 * #%L
 * RAICC
 * 
 * %%
 * Copyright (C) 2020 Jordan Samhi
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
				SootClass sc = stmtMethod.getDeclaringClass();
				if(!LibrariesManager.v().contains(sc.getName())) {
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
		}
		Options.v().set_output_format(Options.output_format_dex);
		Options.v().set_output_dir(Constants.TARGET_TMP_DIR);
		Options.v().set_force_overwrite(true);
		PackManager.v().writeOutput();
	}
}
