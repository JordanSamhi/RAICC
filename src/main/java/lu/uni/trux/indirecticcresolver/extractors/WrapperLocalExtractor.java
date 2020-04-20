package lu.uni.trux.indirecticcresolver.extractors;

import soot.Value;
import soot.jimple.InvokeExpr;

public interface PendingIntentLocalExtractor {
	public Value extractPendingIntentLocal(InvokeExpr inv);
}
