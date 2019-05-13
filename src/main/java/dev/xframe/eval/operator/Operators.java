package dev.xframe.eval.operator;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Operators {
	
	public static enum Arith {
		// level 1
		TERNARY ("?"),
		// level 2
		AND("&"), OR("|"), XOR("~"),
		// level 3
		LOGICAND("&&"), LOGICOR("||"),
		// level 4
		EQUAL("=="), EQUAL_1("="), UNEQUAL("!="), UNEQUAL_1("<>"), SMALLER("<"), LARGER(">"), SMALLEREQ("<="), LARGEREQ(">="),
		// level 5
		LOGICNOT("!"),
		// level 6
		BITSHIFTLEFT("<<"), BITSHIFTRIGHT(">>"),
		// level 7
		PLUS("+"), MINUS("-"),
		// level 8
		MULTIPLY("*"), DIVIDE("/"), MODULUS("%"), 
		// level 9
		POW("^"), 
		// level 10
		FACTORIAL("!"),
		// level 11 function
		// level 12 unary minus
		// level 13 bracket
		;
		
		public final String symbol;
		private Arith(String symbol) {
			this.symbol = symbol;
		}
		public boolean match(String token) {
			return this.symbol.equals(token);
		}
	}
	
	static final Map<Arith, Operator> arithOps = new EnumMap<>(Arith.class);
	static final Map<String, Operator> funcOps = new HashMap<>();
	public static void offer(ArithOp op) {
		arithOps.put(op.arith, op);
	}
	public static void offer(FuncOp op) {
		funcOps.put(op.name, op);
	}
	
	public static Operator get(Arith arith) {
		return arithOps.get(arith);
	}
	public static Operator get(String func) {
		return funcOps.get(func);
	}
	
	static {
		offer(new ArithOp(Arith.TERNARY, args -> args.getAsBool(0) ? args.get(1) : args.get(2)));
		offer(new ArithOp(Arith.AND, args -> args.getAsLong(0) & args.getAsLong(1)));
		offer(new ArithOp(Arith.OR, args -> args.getAsLong(0) | args.getAsLong(1)));
		offer(new ArithOp(Arith.XOR, args -> args.getAsLong(0) ^ args.getAsLong(1)));
		offer(new ArithOp(Arith.LOGICAND, args -> (args.getAsBool(0) && args.getAsBool(1)) ? 1 : 0));
		offer(new ArithOp(Arith.LOGICOR, args -> (args.getAsBool(0) || args.getAsBool(1)) ? 1 : 0));
		offer(new ArithOp(Arith.EQUAL, args -> (args.get(0) == args.get(1)) ? 1 : 0));
		offer(new ArithOp(Arith.EQUAL_1, args -> (args.get(0) == args.get(1)) ? 1 : 0));
		offer(new ArithOp(Arith.UNEQUAL, args -> (args.get(0) != args.get(1)) ? 1 : 0));
		offer(new ArithOp(Arith.UNEQUAL_1, args -> (args.get(0) != args.get(1)) ? 1 : 0));
		offer(new ArithOp(Arith.LARGER, args -> (args.get(0) > args.get(1)) ? 1 : 0));
		offer(new ArithOp(Arith.LARGEREQ, args -> (args.get(0) >= args.get(1)) ? 1 : 0));
		offer(new ArithOp(Arith.SMALLER, args -> (args.get(0) < args.get(1)) ? 1 : 0));
		offer(new ArithOp(Arith.SMALLEREQ, args -> (args.get(0) <= args.get(1)) ? 1 : 0));
		offer(new ArithOp(Arith.LOGICNOT, args -> (!args.getAsBool(0)) ? 1 : 0));
		offer(new ArithOp(Arith.BITSHIFTLEFT, args -> args.getAsLong(0) << args.getAsLong(1)));
		offer(new ArithOp(Arith.BITSHIFTRIGHT, args -> args.getAsLong(0) >> args.getAsLong(1)));
		offer(new ArithOp(Arith.PLUS, args -> args.get(0) + args.get(1)));
		offer(new ArithOp(Arith.MINUS, args -> args.get(0) - args.get(1)));
		offer(new ArithOp(Arith.MULTIPLY, args -> args.get(0) * args.get(1)));
		offer(new ArithOp(Arith.DIVIDE, args -> args.get(0) / args.get(1)));
		offer(new ArithOp(Arith.MODULUS, args -> args.get(0) % args.get(1)));
		offer(new ArithOp(Arith.POW, args -> Math.pow(args.get(0), args.get(1))));
		offer(new ArithOp(Arith.FACTORIAL, args -> Functions.factorial(args.get(0))));
		
		offer(new FuncOp("abs", args -> Math.abs(args.get(0))));
		offer(new FuncOp("exp", args -> Math.exp(args.get(0))));
		offer(new FuncOp("sqrt", args -> Math.sqrt(args.get(0))));
		offer(new FuncOp("sign", args -> Functions.sign(args.get(0))));
		offer(new FuncOp("log", args -> Math.log(args.get(0))));
		offer(new FuncOp("log10", args -> Math.log10(args.get(0))));
		offer(new FuncOp("sin", args -> Math.sin(args.get(0))));
		offer(new FuncOp("cos", args -> Math.cos(args.get(0))));
		offer(new FuncOp("tan", args -> Math.tan(args.get(0))));
		offer(new FuncOp("atan", args -> Math.atan(args.get(0))));
		offer(new FuncOp("round", args -> args.lenIs(1) ? Math.round(args.get(0)) : Functions.round(args.get(0), args.get(1))));
		offer(new FuncOp("ceil", args -> args.lenIs(1) ? Math.ceil(args.get(0)) : Functions.ceil(args.get(0), args.get(1))));
		offer(new FuncOp("floor", args -> args.lenIs(1) ? Math.floor(args.get(0)) : Functions.floor(args.get(0), args.get(1))));
		offer(new FuncOp("pow", args -> Math.pow(args.get(0), args.get(1))));
		offer(new FuncOp("factorial", args -> Functions.factorial(args.get(0))));
		offer(new FuncOp("min", args -> Math.min(args.get(0), args.get(1))));
		offer(new FuncOp("max", args -> Math.max(args.get(0), args.get(1))));
		offer(new FuncOp("rnd", args -> args.lenIs(1) ? Functions.random(args.get(0)) : Functions.random(args.get(0), args.get(1))));
		offer(new FuncOp("random", args -> args.lenIs(1) ? Functions.random(args.get(0)) : Functions.random(args.get(0), args.get(1))));
		offer(new FuncOp("if", args-> args.getAsBool(0) ? args.get(1) : args.get(2)));
	}
	
}
