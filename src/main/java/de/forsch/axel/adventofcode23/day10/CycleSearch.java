package de.forsch.axel.adventofcode23.day10;

import java.util.LinkedList;

import de.forsch.axel.adventofcode23.day10.PipeGrid.Pipe;

public class CycleSearch {

	private final PipeGrid grid;

	private int timestamp;
	private int[] visited;
	private Pipe predecessors[];

	private Pipe[] cycleEnds = new Pipe[2];

	public CycleSearch(PipeGrid grid) {
		super();
		this.grid = grid;
		this.timestamp = 0;
		this.visited = new int[grid.size()];
		this.predecessors = new Pipe[grid.size()];
	}

	public int detectCycle(Pipe source) {
		return detectCycle(source.row, source.column);
	}

	public int detectCycle(int r, int c) {
		timestamp++;
		LinkedList<Pipe> queue = new LinkedList<>();
		LinkedList<Integer> depth = new LinkedList<>();
		LinkedList<Pipe> parents = new LinkedList<>();

		// add source
		Pipe source = grid.get(r, c);
		queue.add(source);
		depth.add(0);
		parents.add(null);
		visited[source.id(grid)] = timestamp;

		while (!queue.isEmpty()) {
			Pipe currentPipe = queue.pollFirst();
			int currentDepth = depth.pollFirst();
			Pipe parent = parents.pollFirst();

			for (Pipe neighbour : grid.getNeighbours(currentPipe)) {
				if (visited[neighbour.id(grid)] < timestamp) {
					visited[neighbour.id(grid)] = timestamp;
					predecessors[neighbour.id(grid)] = currentPipe;
					queue.add(neighbour);
					depth.add(currentDepth + 1);
					parents.add(currentPipe);
				} else if (neighbour != parent) {
					cycleEnds[0] = currentPipe;
					cycleEnds[1] = neighbour;
					return currentDepth + 1;
				}
			}
		}
		return 0;
	}

	public char[][] cycleGrid() {
		if (timestamp == 0) {
			throw new RuntimeException("Execute search first.");
		}

		int[][] toS = new int[2][];
		char[][] cycleGrid = new char[grid.height][grid.width];

		Pipe p = cycleEnds[0];
		while (p.type != 'S') {
			cycleGrid[p.row][p.column] = p.type;
			toS[0] = new int[] { p.row, p.column };
			p = predecessors[p.id(grid)];
		}

		p = cycleEnds[1];
		while (p.type != 'S') {
			cycleGrid[p.row][p.column] = p.type;
			toS[1] = new int[] { p.row, p.column };
			p = predecessors[p.id(grid)];
		}

		cycleGrid[grid.getSource().row][grid.getSource().column] = getSourcePipeType(toS);
		return cycleGrid;
	}

	public char getSourcePipeType(int[][] toSource) {
		Pipe source = grid.getSource();
		int r1 = toSource[0][0] - source.row;
		int c1 = toSource[0][1] - source.column;
		double d1 = Math.atan2(r1, c1) / Math.PI * 2;

		int r2 = toSource[1][0] - source.row;
		int c2 = toSource[1][1] - source.column;
		double d2 = Math.atan2(r2, c2) / Math.PI * 2;

		// d => 0: right, 1: bottom, -1: top, 2: left
		if (d1 == 0 || d2 == 0) {
			if (d1 == 1 || d2 == 1) {
				return 'F';
			}
			if (d1 == 2 || d2 == 2) {
				return '-';
			}
			if (d1 == -1 || d2 == -1) {
				return 'L';
			}
		}
		if (d1 == 1 || d2 == 1) {
			if (d1 == 2 || d2 == 2) {
				return '7';
			}
			if (d1 == -1 || d2 == -1) {
				return '|';
			}
		}
		if (d1 == 2 || d2 == 2) {
			if (d1 == -1 || d2 == -1) {
				return 'J';
			}
		}

		throw new RuntimeException("cannot happen");
	}

	public int getEnclosedArea() {
		if (timestamp == 0) {
			throw new RuntimeException("Execute search first.");
		}

		char[][] cycleGrid = cycleGrid();

		int enclosedArea = 0;

		int count;
		for (int r = 0; r < grid.height; ++r) {
			count = 0;

			for (int c = 0; c < grid.width; ++c) {
				char gridValue = cycleGrid[r][c];
				if (gridValue == 0 && count % 2 == 1) {
					enclosedArea++;
					continue;
				}
				if (gridValue == '|' || gridValue == 'F' || gridValue == '7') {
					count++;
				}
			}
		}

		return enclosedArea;
	}
}
