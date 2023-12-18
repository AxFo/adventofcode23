package de.forsch.axel.adventofcode23.day17;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

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

	public int findPath(GridCoordinate source, GridCoordinate target, CrucibleNeighborhood neighborhood) {
		int[][][][] distances = new int[height][width][4][neighborhood.maxStepsInDirection()];
		PathElement[][][][] predecessors = new PathElement[height][width][4][neighborhood.maxStepsInDirection()];

		for (int r = 0; r < height; ++r) {
			for (int c = 0; c < width; ++c) {
				for (int d = 0; d < 4; ++d) {
					for (int s = 0; s < neighborhood.maxStepsInDirection(); ++s) {
						distances[r][c][d][s] = Integer.MAX_VALUE;
						distances[source.row][source.column][d][s] = 0;
						predecessors[r][c][d][s] = null;
					}
				}
			}
		}

		PriorityQueue<PathElement> q = new PriorityQueue<>();
		q.add(new PathElement(0, Direction.RIGHT, 0, source));
		q.add(new PathElement(0, Direction.DOWN, 0, source));

		while (!q.isEmpty()) {
			PathElement u = q.poll();

			int distU = distances[u.location.row][u.location.column][u.direction.ordinal()][Math.max(0,
					u.stepsInDirection - 1)];

			List<PathElement> neighbors = neighborhood.apply(u);
			for (PathElement v : neighbors) {
				int distUV = v.dist;

				int distSUV = distU + distUV;
				if (distSUV < distances[v.location.row][v.location.column][v.direction.ordinal()][v.stepsInDirection
						- 1]) {
					q.remove(v);
					v.setDistance(distSUV);
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
			for (int s = neighborhood.minStepsInDirection() - 1; s < neighborhood.maxStepsInDirection(); ++s) {
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

	public static abstract class CrucibleNeighborhood implements Function<PathElement, List<PathElement>> {

		private int[][] map;

		public CrucibleNeighborhood(HeatLossMap map) {
			this.map = map.map;
		}

		public abstract int minStepsInDirection();

		public abstract int maxStepsInDirection();

		@Override
		public List<PathElement> apply(PathElement t) {
			List<PathElement> neighbors = new ArrayList<>();
			for (Direction direction : Direction.values()) {
				GridCoordinate loc = t.location.step(direction);
				if (direction != t.direction && direction.ordinal() % 2 == t.direction.ordinal() % 2) {
					continue;
				}
				if (!loc.isValid(map)) {
					continue;
				}
				if (direction != t.direction && t.stepsInDirection < minStepsInDirection()) {
					continue;
				}
				if (direction == t.direction && t.stepsInDirection == maxStepsInDirection()) {
					continue;
				}
				neighbors.add(new PathElement(loc.evaluate(map), direction,
						direction == t.direction ? t.stepsInDirection + 1 : 1, loc));
			}
			return neighbors;
		}
	}

	public static final class NormalCrucibleNeighborhood extends CrucibleNeighborhood {

		public NormalCrucibleNeighborhood(HeatLossMap map) {
			super(map);
		}

		@Override
		public int minStepsInDirection() {
			return 1;
		}

		@Override
		public int maxStepsInDirection() {
			return 3;
		}
	}

	public static final class UltraCrucibleNeighborhood extends CrucibleNeighborhood {

		public UltraCrucibleNeighborhood(HeatLossMap map) {
			super(map);
		}

		@Override
		public int minStepsInDirection() {
			return 4;
		}

		@Override
		public int maxStepsInDirection() {
			return 10;
		}
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
