package dev.xframe.eval.expr;

import dev.xframe.eval.Variables;

public class VarsExpr implements Variables {
	
	final Expr expr;
	
	public VarsExpr(Expr expr) {
		this.expr = expr;
	}

	public double eval() {
		return expr.eval(this);
	}
	
	public long evalAsLong() {
		return (long) eval();
	}
	
	public int evalAsInt() {
		return (int) eval();
	}
	
	public boolean evalAsBool() {
		return eval() > 0;
	}

	
	Entry tail;
	
	public VarsExpr set(String name, double val) {
		if (expr.isConst()) return this;//const do not requires variables
		
		if (tail == null) {
			tail = new Entry(null, name, val);
		} else {
			tail = new Entry(tail, name, val);
		}
		return this;
	}
	
	@Override
	public double get(String name) {
		Entry e = tail;
		while(e != null) {
			if(e.name.equals(name)) {
				return e.value;
			}
			e = e.prev;
		}
		return 0;
	}
	
	static class Entry {
		final Entry prev;
		final String name;
		final double value;
		Entry(Entry prev, String name, double value) {
			this.prev = prev;
			this.name = name;
			this.value = value;
		}
	}

}
