package lu.uni.trux.rhicc.extractors;

import soot.Value;
import soot.jimple.InvokeExpr;

public interface WrapperLocalExtractor {
	public Value extractWrapperLocal(InvokeExpr inv);
}
