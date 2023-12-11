package de.forsch.axel.adventofcode23.day11;

public class Galaxy {

	public final Universe universe;
	private final int r;
	private final int c;

	public Galaxy(Universe universe, int r, int c) {
		super();
		this.universe = universe;
		this.r = r;
		this.c = c;
	}

	public int r() {
		return r + universe.getRowOffset(r);
	}

	public int c() {
		return c + universe.getColumnOffset(c);
	}

	@Override
	public String toString() {
		return "Galaxy [r=" + r() + ", c=" + c() + "]";
	}

//	@Override
//	public String toString() {
//		return "Galaxy [r=" + r + " (" + r() + "), c=" + c + " (" + c() + ")]";
//	}
}
