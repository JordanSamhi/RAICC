package lu.uni.trux.indirecticcresolver.extractors;

import soot.SootMethod;
import soot.Value;
import soot.jimple.InvokeExpr;

public abstract class PendingIntentLocalExtractorImpl implements PendingIntentLocalExtractor {
	private PendingIntentLocalExtractorImpl next;

	public PendingIntentLocalExtractorImpl(PendingIntentLocalExtractorImpl next) {
		this.next = next;
	}
	
	@Override
	public Value extractPendingIntentLocal(InvokeExpr inv) {
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
		if(m.getSignature().equals(this.getMethodSignatureRecognized())) {
			return inv.getArg(this.getIndexHandled());
		}
		return null;
	}
	
	protected abstract String getMethodSignatureRecognized();
	protected abstract int getIndexHandled();
}