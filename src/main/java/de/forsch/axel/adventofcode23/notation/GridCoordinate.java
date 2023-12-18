package de.forsch.axel.adventofcode23.notation;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class GridCoordinate {

	public final int row;
	public final int column;

	public GridCoordinate(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public static <V> int index(GridCoordinate coordinate, V[][] grid) {
		return (coordinate.row * grid[0].length) + coordinate.column;
	}

	public static int index(GridCoordinate coordinate, int[][] grid) {
		return (coordinate.row * grid[0].length) + coordinate.column;
	}

	public static <V> GridCoordinate location(int index, V[][] grid) {
		int r = Math.floorDiv(index, grid[0].length);
		int c = index % grid[0].length;
		return new GridCoordinate(r, c);
	}

	public GridCoordinate step(Direction direction) {
		switch (direction) {
		case DOWN:
			return new GridCoordinate(row + 1, column);
		case LEFT:
			return new GridCoordinate(row, column - 1);
		case RIGHT:
			return new GridCoordinate(row, column + 1);
		case UP:
			return new GridCoordinate(row - 1, column);
		}
		throw new RuntimeException("should be unreachable");
	}

	public boolean isValid(int[][] grid) {
		return row >= 0 && row < grid.length && column >= 0 && column < grid[0].length;
	}

	public <V> V evaluate(V[][] grid) {
		return grid[row][column];
	}

	public char evaluate(char[][] grid) {
		return grid[row][column];
	}

	public int evaluate(int[][] grid) {
		return grid[row][column];
	}

	public boolean evaluate(boolean[][] grid) {
		return grid[row][column];
	}

	@Override
	public String toString() {
		return "(" + row + ", " + column + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		GridCoordinate rhs = (GridCoordinate) obj;
		return new EqualsBuilder()//
				.append(row, rhs.row)//
				.append(column, rhs.column)//
				.isEquals();
	}

	@Override
	public int hashCode() {
		// you pick a hard-coded, randomly chosen, non-zero, odd number
		// ideally different for each class
		return new HashCodeBuilder(123, 781)//
				.append(row)//
				.append(column)//
				.toHashCode();
	}
}
