package de.forsch.axel.adventofcode23.day18;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.forsch.axel.adventofcode23.notation.Direction;
import de.forsch.axel.adventofcode23.notation.GridCoordinate;

public class Lagoon {

	private int rowOffset;
	private int columnOffset;

	private int height;
	private int width;

	private List<Trench> trenches;
	private boolean[][] trenchMap;
	private boolean[][] occupiedMap;

	public Lagoon(List<String> digMap, boolean part1) {
		this.trenches = new ArrayList<>();

		GridCoordinate current = new GridCoordinate(0, 0);
		GridCoordinate next;
		for (String s : digMap) {
			String[] tokens = s.split(" ");
			Direction dir;
			int steps;
			if (part1) {
				dir = Direction.parse(tokens[0]);
				steps = Integer.parseInt(tokens[1]);
//				Color color = Color.decode(tokens[2].substring(1, tokens[2].length() - 1));
			} else {
				steps = Integer.parseInt(tokens[2].substring(2, tokens[2].length() - 2), 16);

				switch (tokens[2].substring(tokens[2].length() - 2, tokens[2].length() - 1)) {
				case "0":
					dir = Direction.RIGHT;
					break;
				case "1":
					dir = Direction.DOWN;
					break;
				case "2":
					dir = Direction.LEFT;
					break;
				case "3":
					dir = Direction.UP;
					break;
				default:
					throw new RuntimeException("Unknown direction: "
							+ tokens[2].substring(tokens[2].length() - 2, tokens[2].length() - 1));
				}
			}
			next = current;
			for (int i = 0; i < steps; ++i) {
				next = next.step(dir);
			}

			trenches.add(new Trench(this, current, next));
			current = next;
		}

		this.rowOffset = trenches.stream().mapToInt(t -> Math.min(t.start.row, t.end.row)).min().getAsInt();
		this.columnOffset = trenches.stream().mapToInt(t -> Math.min(t.start.column, t.end.column)).min().getAsInt();

		this.height = trenches.stream().mapToInt(t -> Math.max(t.start().row, t.end().row)).max().getAsInt() + 1;
		this.width = trenches.stream().mapToInt(t -> Math.max(t.start().column, t.end().column)).max().getAsInt() + 1;

		this.trenchMap = trenchMap();
	}

	public boolean[][] trenchMap() {
		boolean[][] trenchMap = new boolean[height][width];
		for (Trench t : trenches) {
			for (GridCoordinate c : t.occupiedBlocks()) {
				trenchMap[c.row][c.column] = true;
			}
		}
		return trenchMap;
	}

	public void printTrenchMap() {
		for (int r = 0; r < height; ++r) {
			for (int c = 0; c < width; ++c) {
				if (trenchMap[r][c]) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}

	public int fillOccupiedMap() {
		occupiedMap = new boolean[height][width];
		int trenchSize = 0;
		for (int r = 0; r < height; ++r) {
			for (int c = 0; c < width; ++c) {
				if (trenchMap[r][c]) {
					occupiedMap[r][c] = true;
					trenchSize++;
				}
			}
		}

		int count = 0;
		LinkedList<GridCoordinate> queue = new LinkedList<>();
		queue.add(findInsideLocation());

		while (!queue.isEmpty()) {
			GridCoordinate c = queue.pollFirst();
			if (!c.evaluate(occupiedMap)) {
				occupiedMap[c.row][c.column] = true;
				count++;

				for (Direction dir : Direction.values()) {
					queue.add(c.step(dir));
				}
			}
		}
		return count + trenchSize;
	}

//	public int fillOccupiedMap() {
//		GridCoordinate inside = findInsideLocation();
//		occupiedMap = new boolean[height][width];
//		int trenchSize = 0;
//		for (int r = 0; r < height; ++r) {
//			for (int c = 0; c < width; ++c) {
//				if (trenchMap[r][c]) {
//					occupiedMap[r][c] = true;
//					trenchSize++;
//				}
//			}
//		}
//		return trenchSize + floodFill(inside);
//	}
//
//	public int floodFill(GridCoordinate loc) {
//		occupiedMap[loc.row][loc.column] = true;
//
//		int sum = 1;
//		for (Direction dir : Direction.values()) {
//			GridCoordinate next = loc.step(dir);
//			if (!next.evaluate(occupiedMap)) {
//				sum += floodFill(next);
//			}
//		}
//		return sum;
//	}

	public GridCoordinate findInsideLocation() {
		GridCoordinate inside = null;
		found: for (int r = 0; r < height; ++r) {
			int count = 0;
			for (int c = 0; c < width; ++c) {
				if (trenchMap[r][c]) {
					count++;
				} else if (count == 1) {
					inside = new GridCoordinate(r, c);
					break found;
				}
			}
		}
		return inside;
	}

	public void printOccupied() {
		for (int r = 0; r < height; ++r) {
			for (int c = 0; c < width; ++c) {
				if (occupiedMap[r][c]) {
					System.out.print("+");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}

	@Override
	public String toString() {
		return "Lagoon [rowOffset=" + rowOffset + ", columnOffset=" + columnOffset + ", height=" + height + ", width="
				+ width + "]";
	}

	public static class Trench {

		private Lagoon lagoon;

		private GridCoordinate start;
		private GridCoordinate end;

		private Color color;

		public Trench(Lagoon lagoon, GridCoordinate start, GridCoordinate end) {
			super();
			this.lagoon = lagoon;
			this.start = start;
			this.end = end;
		}

		public GridCoordinate start() {
			return new GridCoordinate(start.row - lagoon.rowOffset, start.column - lagoon.columnOffset);
		}

		public GridCoordinate end() {
			return new GridCoordinate(end.row - lagoon.rowOffset, end.column - lagoon.columnOffset);
		}

		public List<GridCoordinate> occupiedBlocks() {
			List<GridCoordinate> occupied = new ArrayList<>();
			for (int r = Math.min(start().row, end().row); r <= Math.max(start().row, end().row); ++r) {
				for (int c = Math.min(start().column, end().column); c <= Math.max(start().column, end().column); ++c) {
					occupied.add(new GridCoordinate(r, c));
				}
			}
			return occupied;
		}

		@Override
		public String toString() {
			return "Trench [start()=" + start() + ", end()=" + end() + ", color=" + color + "]";
		}
	}
}
