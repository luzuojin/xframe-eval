package dev.xframe.eval.error;

public class ParseException extends RuntimeException {

	private static final long serialVersionUID = 4494160542988762303L;

	/**
	 * Create an error with given message id and fill in given string in message
	 * 
	 * @PARAM id id of the message
	 * @PARAM str a string which will be filled in in the message
	 */
	public ParseException(final int id, final String str) {
		col_ = -1;
		id_ = id;

		msg_ = String.format(errorMsg(id_), str);
	}

	/**
	 * Create an error with given message id and fill in given string in message
	 * 
	 * @PARAM id id of the message
	 */
	public ParseException(final int id) {
		col_ = -1;
		id_ = id;

		msg_ = errorMsg(id_);
	}

	/**
	 * Create an error with given message id and fill in given string in message
	 * 
	 * @PARAM row row where the error occured
	 * @PARAM col column where the error occured
	 * @PARAM id id of the message
	 * @PARAM str a string which will be filled in in the message
	 */
	public ParseException(final int col, final int id, final String str) {
		col_ = col;
		id_ = id;

		msg_ = String.format(errorMsg(id_), str);
	}

	/**
	 * Create an error with given message id and fill in given string in message
	 * 
	 * @PARAM row row where the error occured
	 * @PARAM col column where the error occured
	 * @PARAM id id of the message
	 */
	public ParseException(final int col, final int id) {
		col_ = col;
		id_ = id;

		msg_ = errorMsg(id_);
	}

	/**
	 * Returns the error message, including line and column number
	 */
	public final String get() {
		String res;
		if (col_ == -1) {
			res = String.format("Error: %s", msg_);
		} else {
			res = String.format("Error: %s (col %d)", msg_, col_);
		}
		return res;
	}

	int get_id() {
		return id_;
	}

	// / Private functions

	/**
	 * Returns a pointer to the message description for the given message id.
	 * Returns "Unknown error" if id was not recognized.
	 */
	private String errorMsg(final int id) {
		switch (id) {
		// syntax errors
		case 1:
			return "Syntax error in part \"%s\"";
		case 2:
			return "Syntax error";
		case 3:
			return "Parentesis ) missing";
		case 4:
			return "Empty expression";
		case 5:
			return "Unexpected part \"%s\"";
		case 6:
			return "Unexpected end of expression";
		case 7:
			return "Value expected";

			// wrong or unknown operators, functions, variables
		case 101:
			return "Unknown operator %s";
		case 102:
			return "Unknown function %s";
		case 103:
			return "Unknown variable %s";
		case 104:
			return "Unknown operator";
		case 105:
			return "operator dose not had args";
		case 106:
			return "Unknown constant %s";
		case 200:
			return "Too long expression, maximum number of characters exceeded";
		case 300:
			return "Defining variable failed";
		case 400:
			return "Integer value expected in function %s";
		case 500:
			return "%s";
		}
		return "Unknown error";
	}

	private int col_; // / column (position) where the error occured
	private int id_; // / id of the error
	private String msg_;
}
