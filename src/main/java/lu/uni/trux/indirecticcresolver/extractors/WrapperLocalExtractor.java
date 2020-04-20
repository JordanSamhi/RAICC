package lu.uni.trux.indirecticcresolver.extractors;

import soot.Value;
import soot.jimple.InvokeExpr;

public interface WrapperLocalExtractor {
	public Value extractWrapperLocal(InvokeExpr inv);
}
