package dev.xframe.eval.expr;

import dev.xframe.eval.Variables;

public class BracketExpr implements Expr {
	
	final Expr child;

	public BracketExpr(Expr expr) {
		this.child = expr;
	}

	@Override
	public double eval(Variables vars) {
		return child.eval(vars);
	}
	
	@Override
    public String toString() {
        return "(" + child + ")";
    }

}
