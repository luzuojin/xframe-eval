package dev.xframe.eval;

import org.junit.Assert;
import org.junit.Test;

public class EvalTest {
	
	Variables vars = new Variables() {
		@Override
		public double get(String name) {
			switch (name) {
			case "a":
				return 2;
			case "b":
				return 1;
			case "c":
				return 1;
			default:
				return 0;
			}
		}
	};
	
	@Test
	public void testEval() {
		String expr = "2*1+1";
		Assert.assertTrue(3 == Evaluator.eval(expr, null));
	}
	
	@Test
	public void testVars1() {
		String expr = "a*b+c";
		Assert.assertTrue(3 == Evaluator.eval(expr, vars));
	}
	
	@Test
	public void testVars2() {
		String expr = "ceil(1.1)*b+c";
		Assert.assertTrue(3 == Evaluator.eval(expr, vars));
	}

}
