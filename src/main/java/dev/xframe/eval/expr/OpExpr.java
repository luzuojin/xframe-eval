package dev.xframe.eval.expr;

import java.util.Arrays;
import java.util.stream.Collectors;

import dev.xframe.eval.Variables;
import dev.xframe.eval.operator.ArithOp;
import dev.xframe.eval.operator.FuncOp;
import dev.xframe.eval.operator.OpArgs;
import dev.xframe.eval.operator.Operator;
import dev.xframe.eval.operator.Operators.Arith;

public class OpExpr implements Expr {
	
	final Operator op;
	final Expr[] children;

	public OpExpr(Operator op, Expr... exprs) {
		this.op = op;
		this.children = exprs;
	}
	
	@Override
	public double eval(Variables vars) {
		return op.eval(new ExprOpArgs(children, vars));
	}
	
	public static OpExpr of(Operator op, Expr... exprs) {
		return new OpExpr(op, exprs);
	}
	
	@Override
	public String toString() {
		return (op instanceof ArithOp) ? asArithToString() : asFuncToString();
	}

	String asArithToString() {
		Arith arith = ((ArithOp) op).arith;
		if(arith == Arith.FACTORIAL) {
			return children[0] + arith.symbol;
		}
		if(arith == Arith.TERNARY) {
			return children[0] + "?" + children[1] + ":" + children[2];
		}
		if(arith == Arith.LOGICNOT) {
			return arith.symbol + children[0];
		}
		return children[0]  + arith.symbol + children[1];
	}

	String asFuncToString() {
		return ((FuncOp) op).name + "(" + String.join(",", Arrays.stream(children).map(Expr::toString).collect(Collectors.toList())) + ")";
	}
	
	static class ExprOpArgs implements OpArgs {
		final Expr[] children;
		final Variables vars;
		ExprOpArgs(Expr[] children, Variables vars) {
			this.children = children;
			this.vars = vars;
		}
		public int len() {
			return children.length;
		}
		public double get(int index) {
			return children[index].eval(vars);
		}
	}
	
}
