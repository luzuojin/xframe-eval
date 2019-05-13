package dev.xframe.eval.expr;

import dev.xframe.eval.Variables;

public class VarExpr implements Expr{

	final String name;
	
	public VarExpr(String name) {
		this.name = name;
	}

	@Override
	public double eval(Variables vars) {
		return vars.get(name);
	}
	
	@Override
	public String toString() {
		return name;
	}

}
