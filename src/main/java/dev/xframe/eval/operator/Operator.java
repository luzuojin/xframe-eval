package dev.xframe.eval.operator;

@FunctionalInterface
public interface Operator {
	
	double eval(OpArgs args);
	
}
