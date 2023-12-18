package de.forsch.axel.adventofcode23.day17;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import de.forsch.axel.adventofcode23.notation.Direction;
import de.forsch.axel.adventofcode23.notation.GridCoordinate;

public class HeatLossMap {

	public final int height;
	public final int width;
	private int[][] map;

	public HeatLossMap(List<String> lines) {
		this.height = lines.size();
		this.width = lines.get(0).length();

		this.map = new int[height][width];
		for (int r = 0; r < height; ++r) {
			String line = lines.get(r);
			for (int c = 0; c < width; ++c) {
				map[r][c] = Integer.parseInt(String.valueOf(line.charAt(c)));
			}
		}
	}

	public int findPath(GridCoordinate source, GridCoordinate target) {
		int[][][][] distances = new int[height][width][4][3];
		PathElement[][][][] predecessors = new PathElement[height][width][4][3];

		for (int r = 0; r < height; ++r) {
			for (int c = 0; c < width; ++c) {
				for (int d = 0; d < 4; ++d) {
					for (int s = 0; s < 3; ++s) {
						distances[r][c][d][s] = Integer.MAX_VALUE;
						distances[source.row][source.column][d][s] = 0;
						predecessors[r][c][d][s] = null;
					}
				}
			}
		}

//		Set<PathElement> test = new HashSet<>();
//		test.add(new PathElement(1, Direction.RIGHT, 1, new GridCoordinate(0, 1)));
//		test.add(new PathElement(2, Direction.RIGHT, 2, new GridCoordinate(0, 2)));
//		test.add(new PathElement(3, Direction.DOWN, 1, new GridCoordinate(1, 2)));
//		test.add(new PathElement(4, Direction.RIGHT, 1, new GridCoordinate(1, 3)));
//		test.add(new PathElement(5, Direction.RIGHT, 2, new GridCoordinate(1, 4)));
//		test.add(new PathElement(6, Direction.RIGHT, 3, new GridCoordinate(1, 5)));
//		test.add(new PathElement(7, Direction.UP, 1, new GridCoordinate(0, 5)));
//		test.add(new PathElement(8, Direction.RIGHT, 1, new GridCoordinate(0, 6)));
//		test.add(new PathElement(9, Direction.RIGHT, 2, new GridCoordinate(0, 7)));
//		test.add(new PathElement(10, Direction.RIGHT, 3, new GridCoordinate(0, 8)));

		PriorityQueue<PathElement> q = new PriorityQueue<>();
		q.add(new PathElement(0, Direction.RIGHT, 0, source));
//			q.add(new PathElement(0, Direction.DOWN, 1, source));

		while (!q.isEmpty()) {
			PathElement u = q.poll();
//			if (test.contains(u)) {
//				System.out.print("-- " + u);
//				System.out.println();
//			}

			int distU = distances[u.location.row][u.location.column][u.direction.ordinal()][Math.max(0,
					u.stepsInDirection - 1)];
//			System.out.println("u=" + u + ", dist=" + distU);

			List<PathElement> neighbors = u.neighbors(map);
			for (PathElement v : neighbors) {
				int distUV = v.dist;

				int distSUV = distU + distUV;
//				System.out.println("  " + v + ", distUV=" + distUV + ", distSUV=" + distSUV + ", distV="
//						+ distances[v.location.row][v.location.column][v.direction.ordinal()][v.stepsInDirection - 1]);
				if (distSUV < distances[v.location.row][v.location.column][v.direction.ordinal()][v.stepsInDirection
						- 1]) {
					q.remove(v);
					v.setDistance(distSUV);
//					if (distances[v.location.row][v.location.column][v.direction.ordinal()][v.stepsInDirection
//							- 1] < Integer.MAX_VALUE) {
//						System.out.println("    new best path to " + v);
//					} else {
//						System.out.println("    first path to " + v + " found");
//					}
					distances[v.location.row][v.location.column][v.direction.ordinal()][v.stepsInDirection
							- 1] = distSUV;
					predecessors[v.location.row][v.location.column][v.direction.ordinal()][v.stepsInDirection - 1] = u;
					q.add(v);
				}
			}
		}

		Direction minDirection = null;
		int minDist = Integer.MAX_VALUE;
		int minSteps = 0;
		for (Direction d : Direction.values()) {
			for (int s = 0; s < 3; ++s) {
				int currentDist = distances[target.row][target.column][d.ordinal()][s];
				if (currentDist < minDist) {
					minDirection = d;
					minDist = distances[target.row][target.column][d.ordinal()][s];
					minSteps = s + 1;
				}
			}
		}

		LinkedList<PathElement> path = new LinkedList<>();

		PathElement current = new PathElement(minDist, minDirection, minSteps, target);
		path.addFirst(current);

		System.out.println();
		do {
			current = predecessors[current.location.row][current.location.column][current.direction
					.ordinal()][current.stepsInDirection - 1];
			path.addFirst(current);
		} while (!current.location.equals(source));

		char[][] finalPath = new char[height][width];
		for (int r = 0; r < height; ++r) {
			for (int c = 0; c < width; ++c) {
				finalPath[r][c] = String.valueOf(map[r][c]).charAt(0);
			}
		}
		for (PathElement element : path) {
			char ch = '.';
			switch (element.direction) {
			case UP:
				ch = '^';
				break;
			case DOWN:
				ch = 'v';
				break;
			case LEFT:
				ch = '<';
				break;
			case RIGHT:
				ch = '>';
				break;
			}
			finalPath[element.location.row][element.location.column] = ch;
			System.out.println(element);
		}

		System.out.println();
		for (int r = 0; r < height; ++r) {
			for (int c = 0; c < width; ++c) {
				System.out.print(finalPath[r][c]);
			}
			System.out.println();
		}

		return minDist;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < height; ++r) {
			for (int c = 0; c < width; ++c) {
				sb.append(map[r][c]);
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	private static class PathElement implements Comparable<PathElement> {
		public int dist;
		public final Direction direction;
		public final int stepsInDirection;
		public final GridCoordinate location;

		public PathElement(int dist, Direction direction, int stepsInDirection, GridCoordinate location) {
			super();
			this.dist = dist;
			this.stepsInDirection = stepsInDirection;
			this.direction = direction;
			this.location = location;
		}

		public List<PathElement> neighbors(int[][] map) {
			List<PathElement> neighbors = new ArrayList<>();
			for (Direction direction : Direction.values()) {
				GridCoordinate loc = location.step(direction);
				if (direction != this.direction && direction.ordinal() % 2 == this.direction.ordinal() % 2) {
					continue;
				}
				if (!loc.isValid(map)) {
					continue;
				}
				if (direction == this.direction && stepsInDirection == 3) {
					continue;
				}
				neighbors.add(new PathElement(loc.evaluate(map), direction,
						direction == this.direction ? stepsInDirection + 1 : 1, loc));
			}
			return neighbors;
		}

		public void setDistance(int d) {
			this.dist = d;
		}

		@Override
		public int compareTo(PathElement o) {
			return dist - o.dist;
		}

		@Override
		public String toString() {
			return "PathElement [dist=" + dist + ", direction=" + direction + ", stepsInDirection=" + stepsInDirection
					+ ", location=" + location + "]";
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
			PathElement rhs = (PathElement) obj;
			return new EqualsBuilder()//
					.append(stepsInDirection, rhs.stepsInDirection)//
					.append(direction, rhs.direction)//
					.append(location, rhs.location)//
					.isEquals();
		}

		@Override
		public int hashCode() {
			// you pick a hard-coded, randomly chosen, non-zero, odd number
			// ideally different for each class
			return new HashCodeBuilder(13, 71)//
					.append(stepsInDirection)//
					.append(direction)//
					.append(location)//
					.toHashCode();
		}
	}
}
