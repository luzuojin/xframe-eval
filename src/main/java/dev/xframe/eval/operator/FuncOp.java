package dev.xframe.eval.operator;

public class FuncOp implements Operator {

	public final String name;
	public final Operator evalfunc;
	public FuncOp(String name, Operator evalfunc) {
		this.name = name;
		this.evalfunc = evalfunc;
	}

	@Override
	public double eval(OpArgs args) {
		return evalfunc.eval(args);
	}

}
