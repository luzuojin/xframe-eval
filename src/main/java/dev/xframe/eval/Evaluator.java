package dev.xframe.eval;

import java.util.Scanner;

import dev.xframe.eval.expr.Expr;

public class Evaluator {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String expr = "";
		do {
			// request an expression
			System.out.print("> ");
			expr = in.nextLine();

			if (expr.length() > 0) {
				// evaluate the expression
				Expr e = new Parser().parse(expr);
				double ret = e.eval(name->1);
				System.out.printf("\t Ans = %g\r\n", ret);
			}
		} while (expr.length() > 0);
		
		in.close();
	}
	
	public static Expr parse(String expr) {
		return new Parser().parse(expr);
	}
	
	public static double eval(String expr, Variables vars) {
		return parse(expr).eval(vars);
	}
	
}
