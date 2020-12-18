package lu.uni.trux.raicc.extractors;

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

import java.util.ArrayList;
import java.util.List;

import soot.SootMethod;
import soot.Value;
import soot.jimple.InvokeExpr;

public abstract class WrapperLocalExtractorImpl implements WrapperLocalExtractor {
	private WrapperLocalExtractorImpl next;
	protected List<String> methods;

	public WrapperLocalExtractorImpl(WrapperLocalExtractorImpl next) {
		this.next = next;
		this.methods = new ArrayList<String>();
	}
	
	@Override
	public Value extractWrapperLocal(InvokeExpr inv) {
		Value v = this.extract(inv);
		
		if(v != null) {
			return v;
		}else if(this.next != null) {
			return this.next.extractWrapperLocal(inv);
		}else {
			return null;
		}
	}
	
	private Value extract(InvokeExpr inv) {
		SootMethod m = inv.getMethod();
		if(this.canHandleMethod(m.getSignature())) {
			return inv.getArg(this.getIndexHandled());
		}
		return null;
	}
	
	protected boolean canHandleMethod(String m) {
		return this.methods.contains(m);
	}
	
	protected abstract int getIndexHandled();
}