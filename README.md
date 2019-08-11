#### xframe-eval
--
xframe-eval is java library for evaluating simple mathematical expressions. supports simple exprssions and exprssions with variables.

##### Baisc Usage
```java
//simple expr
String exprStr1 = "2 * 1 + 1";
3 == Evaluator.eval(exprStr1);

//with variables
String exprStr2 = "a * b + c"
Variables vars = new Variables(){
		public double get(String name) {
			switch(name) {
			case "a":
				return 2;
			case "b":
				return 1;
			case "c":
				return 1;
			}
			return 0;
		}
}
3 == Evaluator.eval(exprStr2, vars);

//with functions
String exprStr3 = "ceil(1.1) * 1 + 1"
3 == Evaluator.eval(exprStr3);

//use cached Expr
Expr expr = Evaluator.parse(exprStr);
double result = expr.eval(vars);
```