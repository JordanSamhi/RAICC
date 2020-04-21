package lu.uni.trux.indirecticcresolver.extractors;

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
			return this.next.extract(inv);
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