package dev.xframe.eval.expr;

import dev.xframe.eval.Variables;

public interface Expr {
	
	double eval(Variables vars);
	
	default long evalAsLong(Variables vars) {
		return (long) eval(vars);
	}
	
	default int evalAsInt(Variables vars) {
		return (int) eval(vars);
	}

	default boolean evalAsBool(Variables vars) {
		return eval(vars) > 0;
	}
	
	default VarsExpr toVarsExpr() {
		return new VarsExpr(this);
	}
	
	default VarsExpr set(String name, double val) {
		return toVarsExpr().set(name, val);
	}
	
	default boolean isConst() {
		return false;
	}
	
}
