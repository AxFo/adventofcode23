package de.forsch.axel.adventofcode23.notation;

public enum Direction {
	UP, RIGHT, DOWN, LEFT;

	public static Direction parse(String s) {
		switch (s) {
		case "R":
			return Direction.RIGHT;
		case "L":
			return Direction.LEFT;
		case "U":
			return Direction.UP;
		case "D":
			return Direction.DOWN;
		default:
			throw new RuntimeException("Unknown direction string: " + s);
		}
	}
}