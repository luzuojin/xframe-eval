package dev.xframe.eval.expr;

import java.text.DecimalFormat;

import dev.xframe.eval.Variables;

public class ValExpr implements Expr {

	final double value;
	
	public ValExpr(double value) {
		this.value = value;
	}
	
	@Override
	public double eval(Variables vars) {
		return value;
	}
	
	@Override
	public boolean isConst() {
		return true;
	}

	@Override
	public String toString() {
		return new DecimalFormat().format(value);
	}
	
}
