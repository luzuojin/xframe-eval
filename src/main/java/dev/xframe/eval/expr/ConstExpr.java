package dev.xframe.eval.expr;

import dev.xframe.eval.Variables;
import dev.xframe.eval.error.ParseException;

public class ConstExpr implements Expr {
	
	final String name;
	final double value;
	
	public ConstExpr(String name, double value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public double eval(Variables vars) {
		return value;
	}
	
	public static boolean is(String name) {
		return "PI".equalsIgnoreCase(name) || "E".equalsIgnoreCase(name);
	}
	
	public static ConstExpr of(String name) {
		if("PI".equalsIgnoreCase(name)) {
			return new ConstExpr("PI", Math.PI);
		}
		if("E".equalsIgnoreCase(name)) {
			return new ConstExpr("E", Math.E);
		}
		throw new ParseException(106, name);
	}
	
	@Override
	public boolean isConst() {
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

}
