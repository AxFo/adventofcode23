package de.forsch.axel.adventofcode23.day10;

import java.util.LinkedList;

import de.forsch.axel.adventofcode23.day10.PipeGrid.Pipe;

public class GridSearch {

	private final PipeGrid grid;

	private int timestamp;
	private int[] visited;
	private Pipe predecessors[];

	private Pipe[] cycleEnds = new Pipe[2];

	public GridSearch(PipeGrid grid) {
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

		char[][] cycleGrid = new char[grid.height][grid.width];

		Pipe p = cycleEnds[0];
		while (p.type != 'S') {
			cycleGrid[p.row][p.column] = p.type;
			p = predecessors[p.id(grid)];
		}

		p = cycleEnds[1];
		while (p.type != 'S') {
			cycleGrid[p.row][p.column] = p.type;
			p = predecessors[p.id(grid)];
		}

		cycleGrid[grid.getSource().row][grid.getSource().column] = 'S';
		return cycleGrid;
	}

	public int getEnclosedArea() {
		if (timestamp == 0) {
			throw new RuntimeException("Execute search first.");
		}
		int enclosedArea = 0;

		int[][] enclosed = new int[grid.height][grid.width];
		char[][] cycleGrid = cycleGrid();

		int insideFound = 0;
		for (int r = 0; r < grid.height; ++r) {
			insideFound = 0;
			for (int c = 0; c < grid.width; ++c) {
				if (cycleGrid[r][c] != 0 && insideFound == 0) {
					insideFound = 1;
					continue;
				}
				if (cycleGrid[r][c] != 0 && insideFound > 0) {
					if (cycleGrid[r][c] == '7' || cycleGrid[r][c] == '|' || cycleGrid[r][c] == 'J') {
						insideFound--;
						continue;
					}
					if (cycleGrid[r][c] == 'F' || cycleGrid[r][c] == 'L') {
						insideFound++;
						continue;
					}
					continue;
				}
				if (insideFound == 1) {
					enclosed[r][c]++;
				}
			}
		}

		insideFound = 0;
		for (int c = 0; c < grid.width; ++c) {
			insideFound = 0;
			for (int r = 0; r < grid.height; ++r) {
				if (cycleGrid[r][c] != 0 && insideFound == 0) {
					insideFound = 1;
					continue;
				}
				if (cycleGrid[r][c] != 0 && insideFound > 0) {
					if (cycleGrid[r][c] == 'L' || cycleGrid[r][c] == '-' || cycleGrid[r][c] == 'J') {
						insideFound--;
						continue;
					}
					if (cycleGrid[r][c] == 'F' || cycleGrid[r][c] == '7') {
						insideFound++;
						continue;
					}
					continue;
				}
				if (insideFound == 1) {
					enclosed[r][c] += 2;
					if (enclosed[r][c] == 3) {
						enclosedArea++;
					}
				}
			}
		}

		return enclosedArea;
	}
}
