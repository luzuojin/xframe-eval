package dev.xframe.eval.operator;

public interface OpArgs {
	
	public int len();
	
	default boolean lenIs(int c) {
		return len() == c;
	}
	
	public double get(int index);

	default int getAsInt(int index) {
		return (int) get(index);
	}
	default long getAsLong(int index) {
		return (long) get(index);
	}
	default boolean getAsBool(int index) {
		return get(index) > 0;
	}

}
