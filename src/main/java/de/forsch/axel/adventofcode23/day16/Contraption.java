package de.forsch.axel.adventofcode23.day16;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import de.forsch.axel.adventofcode23.notation.Direction;

public class Contraption {

	public final int height;
	public final int width;
	private char[][] grid;
	private boolean[][][] energized;

	private List<LaserBeam> beams;

	public Contraption(List<String> lines, int sourceRow, int sourceColumn, Direction sourceDirection) {
		this.height = lines.size();
		this.width = lines.get(0).length();

		this.grid = new char[height][width];
		for (int r = 0; r < height; ++r) {
			String line = lines.get(r);
			for (int c = 0; c < width; ++c) {
				grid[r][c] = line.charAt(c);
			}
		}

		List<Direction> deflectedDirections = deflect(sourceDirection, this.grid[sourceRow][sourceColumn]);

		this.beams = new ArrayList<>();
		this.energized = new boolean[height][width][4];
		for (Direction dir : deflectedDirections) {
			this.beams.add(new LaserBeam(this, sourceRow, sourceColumn, dir));
			this.energize(sourceRow, sourceColumn, dir);
		}
	}

	public Contraption run() {
		while (!beams.isEmpty()) {
			step();
		}
		return this;
	}

	private void step() {
		List<LaserBeam> newBeams = new ArrayList<>();
		for (LaserBeam beam : beams) {
			int r = -1;
			int c = -1;
			switch (beam.direction) {
			case DOWN:
				r = beam.row + 1;
				c = beam.column;
				break;
			case LEFT:
				r = beam.row;
				c = beam.column - 1;
				break;
			case RIGHT:
				r = beam.row;
				c = beam.column + 1;
				break;
			case UP:
				r = beam.row - 1;
				c = beam.column;
				break;
			}

			if (r < 0 || r >= height || c < 0 || c >= width) {
				continue;
			}

			boolean energized = energize(r, c, beam.direction);
			if (!energized) {
				continue;
			}

			List<Direction> deflectedDirections = deflect(beam.direction, grid[r][c]);
			for (Direction dir : deflectedDirections) {
				newBeams.add(new LaserBeam(this, r, c, dir));
			}
		}
		this.beams = newBeams;
	}

	private List<Direction> deflect(Direction incomingDirection, char element) {
		List<Direction> deflectedDirections = new ArrayList<>(2);
		switch (element) {
		case '-':
			if (incomingDirection == Direction.LEFT || incomingDirection == Direction.RIGHT) {
				// no change of direction
				deflectedDirections.add(incomingDirection);
			} else {
				deflectedDirections.add(Direction.LEFT);
				deflectedDirections.add(Direction.RIGHT);
			}
			break;
		case '|':
			if (incomingDirection == Direction.UP || incomingDirection == Direction.DOWN) {
				// no change of direction
				deflectedDirections.add(incomingDirection);
			} else {
				deflectedDirections.add(Direction.UP);
				deflectedDirections.add(Direction.DOWN);
			}
			break;
		case '.':
			// no change of direction
			deflectedDirections.add(incomingDirection);
			break;
		case '\\':
			switch (incomingDirection) {
			case DOWN:
				deflectedDirections.add(Direction.RIGHT);
				break;
			case LEFT:
				deflectedDirections.add(Direction.UP);
				break;
			case RIGHT:
				deflectedDirections.add(Direction.DOWN);
				break;
			case UP:
				deflectedDirections.add(Direction.LEFT);
				break;
			}
			break;
		case '/':
			switch (incomingDirection) {
			case DOWN:
				deflectedDirections.add(Direction.LEFT);
				break;
			case LEFT:
				deflectedDirections.add(Direction.DOWN);
				break;
			case RIGHT:
				deflectedDirections.add(Direction.UP);
				break;
			case UP:
				deflectedDirections.add(Direction.RIGHT);
				break;
			}
			break;
		default:
			throw new RuntimeException("unknown element: " + element);
		}
		return deflectedDirections;
	}

	private boolean energize(int row, int column, Direction direction) {
		if (this.energized[row][column][direction.ordinal()]) {
			return false;
		}
		this.energized[row][column][direction.ordinal()] = true;
		return true;
	}

	public int energy() {
		int energizedCount = 0;
		for (int r = 0; r < height; ++r) {
			int row = r;
			for (int c = 0; c < width; ++c) {
				int column = c;

				int count = (int) IntStream.range(0, 4)//
						.mapToObj(idx -> energized[row][column][idx])//
						.filter(e -> e == true)//
						.count();
				if (count > 0) {
					energizedCount++;
				}
			}
		}
		return energizedCount;
	}

	public void printEnergized() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < height; ++r) {
			int row = r;
			for (int c = 0; c < width; ++c) {
				int column = c;
				if (this.grid[r][c] != '.') {
					sb.append(this.grid[r][c]);
					continue;
				}

				int count = (int) IntStream.range(0, 4)//
						.mapToObj(idx -> energized[row][column][idx])//
						.filter(e -> e == true)//
						.count();
				if (count == 0) {
					sb.append('.');
				} else if (count == 1) {
					if (energized[row][column][0]) {
						sb.append("^");
					} else if (energized[row][column][1]) {
						sb.append(">");
					} else if (energized[row][column][2]) {
						sb.append("v");
					} else {
						sb.append("<");
					}
				} else {
					sb.append(count);
				}
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}
