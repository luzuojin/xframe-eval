package dev.xframe.eval.operator;

import java.util.Random;

import dev.xframe.eval.error.ParseException;

/**
 * Additional functions
 */

public class Functions {
	
    private static final Random RND = new Random();
    
	/**
	 * calculate factorial of value for example 5! = 5*4*3*2*1 = 120
	 */
	public static double factorial(double value) {
		double res;
		int v = (int) value;

		if (value != v) {
			throw new ParseException(400, "factorial");
		}

		res = v;
		v--;
		while (v > 1) {
			res *= v;
			v--;
		}

		if (res == 0)
			res = 1; // 0! is per definition 1
		return res;
	}

	/**
	 * calculate the modulus of the given values
	 */
	public static double modulus(double a, double b) {
		// values must be integer
		int a_int = (int) a;
		int b_int = (int) b;
		if (a_int == a && b_int == b) {
			return a_int % b_int;
		} else {
			throw new ParseException(400, "%");
		}
	}

	public static double round(double a, double b) {
		return Math.round(a / b) * b;
	}
	
	public static double ceil(double a, double b) {
		return Math.ceil(a / b) * b;
	}
	
	public static double floor(double a, double b) {
		return Math.floor(a / b) * b;
	}
	
	/**
	 * calculate the sign of the given value
	 */
	public static double sign(double value) {
		if (value > 0)
			return 1;
		if (value < 0)
			return -1;
		return 0;
	}
	
	public static double random(double max) {
	    return random(0, max);
	}
	
	/**
	 * close [min, max]
	 * @param min
	 * @param max
	 * @return
	 */
	public static double random(double min, double max) {
	    int min_int = (int) min;
	    int max_int = (int) max;
	    if (min_int == min && max_int == max) {
	        return  min_int > max_int ?
	                RND.nextInt(min_int - max_int + 1) + max_int:
	                RND.nextInt(max_int - min_int + 1) + min_int;
        } else {
            throw new ParseException(400, "Random");
        }
	}
	
}
