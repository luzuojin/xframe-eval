package dev.xframe.eval.operator;

import dev.xframe.eval.operator.Operators.Arith;

public final class ArithOp implements Operator {

	public final Arith arith;
	public final Operator evalfunc;
	
	public ArithOp(Arith arith, Operator evalfunc) {
		this.arith = arith;
		this.evalfunc = evalfunc;
	}
	
	@Override
	public double eval(OpArgs args) {
		return evalfunc.eval(args);
	}

}
