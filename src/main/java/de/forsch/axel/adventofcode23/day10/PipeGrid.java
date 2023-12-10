package de.forsch.axel.adventofcode23.day10;

import java.util.ArrayList;
import java.util.List;

public class PipeGrid {

	public final int width;
	public final int height;

	private Pipe[][] grid;

	private Pipe source;

	public PipeGrid(List<String> lines) {
		this.width = lines.get(0).length();
		this.height = lines.size();
		initializeGrid(lines);
	}

	public void initializeGrid(List<String> lines) {
		this.grid = new Pipe[height][width];
		for (int r = 0; r < height; ++r) {
			String line = lines.get(r);
			if (line.length() != width) {
				throw new RuntimeException("Row lenghts not consistent");
			}

			for (int c = 0; c < width; ++c) {
				if (line.charAt(c) == '.') {
					continue;
				}
				Pipe pipe = new Pipe(line.charAt(c), r, c);
				if (pipe.type == 'S') {
					source = pipe;
				}
				grid[r][c] = pipe;
			}
		}
	}

	public Pipe get(int row, int column) {
		return grid[row][column];
	}

	public Pipe getSource() {
		return source;
	}

	public List<Pipe> getNeighbours(Pipe pipe) {
		List<Pipe> neighbours = new ArrayList<>();
		if (pipe.connections[0] && pipe.row > 0) {
			Pipe p = get(pipe.row - 1, pipe.column);
			if (p != null && p.connections[2]) {
				neighbours.add(p);
			}
		}
		if (pipe.connections[1] && pipe.column < width - 1) {
			Pipe p = get(pipe.row, pipe.column + 1);
			if (p != null && p.connections[3]) {
				neighbours.add(p);
			}
		}
		if (pipe.connections[2] && pipe.row < height - 1) {
			Pipe p = get(pipe.row + 1, pipe.column);
			if (p != null && p.connections[0]) {
				neighbours.add(p);
			}
		}
		if (pipe.connections[3] && pipe.column > 0) {
			Pipe p = get(pipe.row, pipe.column - 1);
			if (p != null && p.connections[1]) {
				neighbours.add(p);
			}
		}
		return neighbours;
	}

	public int size() {
		return width * height;
	}

	public static final class Pipe {
		public final char type;
		public final int row;
		public final int column;

		private boolean[] connections; // N, E, S, W

		public Pipe(char type, int row, int column) {
			this.type = type;
			this.row = row;
			this.column = column;
			initializeConnections(type);
		}

		public int id(int width) {
			return row * width + column;
		}

		public int id(PipeGrid grid) {
			return row * grid.width + column;
		}

		private void initializeConnections(char type) {
			this.connections = new boolean[4];
			switch (type) {
			case '|':
				connections[0] = true;
				connections[2] = true;
				return;
			case '-':
				connections[1] = true;
				connections[3] = true;
				return;
			case 'L':
				connections[0] = true;
				connections[1] = true;
				return;
			case 'J':
				connections[0] = true;
				connections[3] = true;
				return;
			case '7':
				connections[2] = true;
				connections[3] = true;
				return;
			case 'F':
				connections[1] = true;
				connections[2] = true;
				return;
			case 'S':
				connections[0] = true;
				connections[1] = true;
				connections[2] = true;
				connections[3] = true;
				return;
			default:
				throw new IllegalArgumentException(type + " is no valid node type");
			}
		}

		@Override
		public String toString() {
			return "Pipe [type=" + type + ", row=" + row + ", column=" + column + "]";
		}
	}
}
