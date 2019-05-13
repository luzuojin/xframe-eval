package dev.xframe.eval;

import static dev.xframe.eval.operator.Operators.Arith.AND;
import static dev.xframe.eval.operator.Operators.Arith.BITSHIFTLEFT;
import static dev.xframe.eval.operator.Operators.Arith.BITSHIFTRIGHT;
import static dev.xframe.eval.operator.Operators.Arith.DIVIDE;
import static dev.xframe.eval.operator.Operators.Arith.EQUAL;
import static dev.xframe.eval.operator.Operators.Arith.EQUAL_1;
import static dev.xframe.eval.operator.Operators.Arith.FACTORIAL;
import static dev.xframe.eval.operator.Operators.Arith.LARGER;
import static dev.xframe.eval.operator.Operators.Arith.LARGEREQ;
import static dev.xframe.eval.operator.Operators.Arith.LOGICAND;
import static dev.xframe.eval.operator.Operators.Arith.LOGICNOT;
import static dev.xframe.eval.operator.Operators.Arith.LOGICOR;
import static dev.xframe.eval.operator.Operators.Arith.MINUS;
import static dev.xframe.eval.operator.Operators.Arith.MODULUS;
import static dev.xframe.eval.operator.Operators.Arith.MULTIPLY;
import static dev.xframe.eval.operator.Operators.Arith.OR;
import static dev.xframe.eval.operator.Operators.Arith.PLUS;
import static dev.xframe.eval.operator.Operators.Arith.POW;
import static dev.xframe.eval.operator.Operators.Arith.SMALLER;
import static dev.xframe.eval.operator.Operators.Arith.SMALLEREQ;
import static dev.xframe.eval.operator.Operators.Arith.UNEQUAL;
import static dev.xframe.eval.operator.Operators.Arith.UNEQUAL_1;
import static dev.xframe.eval.operator.Operators.Arith.XOR;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import dev.xframe.eval.error.ParseException;
import dev.xframe.eval.expr.BracketExpr;
import dev.xframe.eval.expr.ConstExpr;
import dev.xframe.eval.expr.Expr;
import dev.xframe.eval.expr.NegExpr;
import dev.xframe.eval.expr.OpExpr;
import dev.xframe.eval.expr.ValExpr;
import dev.xframe.eval.expr.VarExpr;
import dev.xframe.eval.operator.Operators;
import dev.xframe.eval.operator.Operators.Arith;

public class Parser {
	
	private enum TokenType {
		NONE, MINUS, DELIMITER, NUMBER, VARIABLE, FUNCTION, BRACKET_LEFT, BRACKET_RIGHT, COMMA, TERNARY_CON, TERNARY_BR
	}
	
	private String expr;
	private int pos;
	private char cha;
	private String token;
	private TokenType tokenType;
	
	synchronized Expr parse(String expr) {
		this.expr = expr;
		this.pos = -1;
		this.cha = '\0';
		this.token = "";
		this.tokenType = TokenType.NONE;
		getChar();
		return parse_level0();
	}
	
	/**
	 * checks if the given char c is whitespace whitespace when space chr(32) or
	 * tab chr(9)
	 */
	boolean isWhiteSpace(final char c) {
		return c == ' ' || c == '\t'; // space or tab
	}

	/**
	 * checks if the given char c is a delimiter minus is checked apart, can be unary minus
	 */
	boolean isDelimiter(final char c) {
		return "&|<>=+/*%^!".indexOf(c) != -1;
	}

	/**
	 * checks if the given char c is a letter or undersquare
	 */
	boolean isAlpha(final char c) {
		char cUpper = Character.toUpperCase(c);
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ_".indexOf(cUpper) != -1;
	}

	/**
	 * checks if the given char c is a digit or dot
	 */
	boolean isDigitDot(final char c) {
		return "0123456789.".indexOf(c) != -1;
	}

	/**
	 * checks if the given char c is a digit
	 */
	boolean isDigit(final char c) {
		return "0123456789".indexOf(c) != -1;
	}

	/**
	 * Get the next character from the expression. The character is stored into
	 * the char expr_c. If the end of the expression is reached, the function
	 * puts zero ('\0') in expr_c.
	 */
	void getChar() {
		pos++;
		if (pos < expr.length()) {
			cha = expr.charAt(pos);
		} else {
			cha = '\0';
		}
	}

	void getToken() {
		tokenType = TokenType.NONE;
		token = ""; // set token empty

		// skip over whitespaces
		skipWhiteSpace();

		// check for end of expression
		if (cha == '\0') {
			// token is empty
			tokenType = TokenType.DELIMITER;
			return;
		}

		// check for minus
		if (cha == '-') {
			tokenType = TokenType.MINUS;
			token += cha;
			getChar();
			return;
		}

		// check for parentheses
		if (cha == '(') {
			tokenType = TokenType.BRACKET_LEFT;
			token += cha;
			getChar();
			return;
		}
		
		if(cha == ',') {
			tokenType = TokenType.COMMA;
			token += cha;
			getChar();
			return;
		}
		
		if (cha == ')') {
			tokenType = TokenType.BRACKET_RIGHT;
			token += cha;
			getChar();
			return;
		}
		
		if(cha == '?') {
		    tokenType = TokenType.TERNARY_CON;
		    token += cha;
		    getChar();
		    return;
		}
		
		if(cha == ':') {
		    tokenType = TokenType.TERNARY_BR;
		    token += cha;
		    getChar();
		    return;
		}

		// check for operators (delimiters)
		if (isDelimiter(cha)) {
			tokenType = TokenType.DELIMITER;
			while (isDelimiter(cha)) {
				token += cha;
				getChar();
			}
			return;
		}

		// check for a value
		if (isDigitDot(cha)) {
			tokenType = TokenType.NUMBER;
			while (isDigitDot(cha)) {
				token += cha;
				getChar();
			}

			// check for scientific notation like "2.3e-4" or "1.23e50"
			if (cha == 'e' || cha == 'E') {
				token += cha;
				getChar();

				if (cha == '+' || cha == '-') {
					token += cha;
					getChar();
				}

				while (isDigit(cha)) {
					token += cha;
					getChar();
				}
			}

			return;
		}

		// check for variables or functions
		if (isAlpha(cha)) {
			while (isAlpha(cha) || isDigit(cha)) {
				token += cha;
				getChar();
			}

			skipWhiteSpace();

			// check the next non-whitespace character
			if (cha == '(') {
				tokenType = TokenType.FUNCTION;
			} else {
				tokenType = TokenType.VARIABLE;
			}

			return;
		}

		// something unknown is found, wrong characters -> a syntax ParseException
		while (cha != '\0') {
			token += cha;
			getChar();
		}
		
		throw new ParseException(col(), 1, token);
	}

	void skipWhiteSpace() {
		while (isWhiteSpace(cha)) { // space or tab
			getChar();
		}
	}

	int col() {
		return pos - token.length() + 1;
	}
	
	//token as arith
	Arith asArith(Arith... expects) {
		for (Arith expect : expects) {
			if(expect.match(token)) {
				return expect;
			}
		}
		return null;
	}
	
	Expr parse_level0() {//get a token and parse
		getToken();
		return parse_level1();
	}
	
	Expr parse_levelx(Supplier<Expr> next, Arith... expects) {
		Expr ans = next.get();
		Arith arith;
		while((arith = asArith(expects)) != null) {
			Expr left = ans;
			getToken();
			Expr right = next.get();
			ans = OpExpr.of(Operators.get(arith), left, right);
		}
		return ans;
	}
	
	//ternary ? :
	Expr parse_level1() {
		Expr ans = parse_level2();
		while(tokenType == TokenType.TERNARY_CON) {
			Expr cond = ans;
			getToken();
	        Expr left = parse_level2();
	        assert(tokenType == TokenType.TERNARY_BR); // ':'
	        getToken();
	        Expr right = parse_level2();
	        ans = OpExpr.of(Operators.get(Arith.TERNARY), cond, left, right);
		}
		return ans;
	}

	Expr parse_level2() {
		return parse_levelx(this::parse_level3, AND, OR, XOR);
	}

	Expr parse_level3() {
		return parse_levelx(this::parse_level4, LOGICAND, LOGICOR);
	}

	Expr parse_level4() {
		return parse_levelx(this::parse_level5, EQUAL, EQUAL_1, UNEQUAL, UNEQUAL_1, LARGER, LARGEREQ, SMALLER, SMALLEREQ);
	}

	Expr parse_level5() {
		Expr ans = null;
		while(asArith(LOGICNOT) != null) {
			getToken();
			ans = OpExpr.of(Operators.get(LOGICNOT), parse_level6());
		}
		if(ans == null)
			ans = parse_level6();
		return ans;
	}

	Expr parse_level6() {
		return parse_levelx(this::parse_level7, BITSHIFTLEFT, BITSHIFTRIGHT);
	}

	Expr parse_level7() {
		return parse_levelx(this::parse_level8, PLUS, MINUS);
	}

	Expr parse_level8() {
		return parse_levelx(this::parse_level9, MULTIPLY, DIVIDE, MODULUS);
	}
	
	Expr parse_level9() {
		return parse_levelx(this::parse_level10, POW);
	}
	
	Expr parse_level10() {
		Expr ans = parse_level11();
		while (asArith(FACTORIAL) != null) {
			getToken();
			ans = OpExpr.of(Operators.get(FACTORIAL), ans);
		}
		return ans;
	}

	Expr parse_level11() {
		Expr ans = null;
		while(tokenType == TokenType.MINUS) {
			getToken();
			ans = new NegExpr(parse_level12());
		}
		if(ans == null)
			ans = parse_level12();
		return ans;
	}

	Expr parse_level12() {
		if(tokenType == TokenType.FUNCTION) {
			String func = token;
			getToken();
			return OpExpr.of(Operators.get(func), parse_func_args());
		} else {
			return parse_level13();
		}
	}

	Expr[] parse_func_args() {
		if(tokenType == TokenType.BRACKET_LEFT) {
			assert("(".equals(token));
			List<Expr> nodes = new ArrayList<Expr>();
			
			nodes.add(parse_level0());
			
			while(tokenType == TokenType.COMMA) {
				nodes.add(parse_level0());
			}
			
			if (tokenType != TokenType.BRACKET_RIGHT) {
				throw new ParseException(col(), 3);
			}
			
			getToken();//skip )
			return nodes.toArray(new Expr[nodes.size()]);
		}
		throw new ParseException(col(), 3);
	}
	
	Expr parse_level13() {
		if(tokenType == TokenType.BRACKET_LEFT) {
			Expr ans = parse_level0();
			if (tokenType != TokenType.BRACKET_RIGHT) {
				throw new ParseException(col(), 3);
			}
			getToken();
			return new BracketExpr(ans);
		}
		return parse_number();
	}

	Expr parse_number() {
		if(tokenType == TokenType.NUMBER) {
			Expr ans = new ValExpr(Double.parseDouble(token));
			getToken();
			return ans;
		} else if(tokenType == TokenType.VARIABLE) {
			Expr ans = ConstExpr.is(token) ? ConstExpr.of(token) : new VarExpr(token);
			getToken();
			return ans;
		} else {
			if (token.length() == 0) {
				throw new ParseException(col(), 6);
			} else {
				throw new ParseException(col(), 7);
			}
		}
	}
	
}
