package de.forsch.axel.adventofcode23.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Universe {

	public final int initialWidth;
	public final int initialHeight;

	private List<Galaxy> galaxies;
	private int[] rowOffsets;
	private int[] columnOffsets;

	private int expansionFactor;

	public Universe(int initialWidth, int initialHeight) {
		super();
		this.initialWidth = initialWidth;
		this.initialHeight = initialHeight;
		this.expansionFactor = 2;

		this.galaxies = new ArrayList<>();
		this.columnOffsets = new int[initialWidth];
		this.rowOffsets = new int[initialHeight];
	}

	public void setExpansionFactor(int expansionFactor) {
		this.expansionFactor = expansionFactor;
	}

	public long sumOfDistances() {
		long sum = 0;

		for (int i = 0; i < galaxies.size(); ++i) {
			for (int j = i + 1; j < galaxies.size(); ++j) {
				sum += Math.abs(galaxies.get(j).r() - galaxies.get(i).r());
				sum += Math.abs(galaxies.get(j).c() - galaxies.get(i).c());
			}
		}

		return sum;
	}

	public static Universe parse(List<String> lines) {
		if (lines == null || lines.isEmpty()) {
			throw new RuntimeException("Nothing to parse.");
		}

		Universe universe = new Universe(lines.get(0).length(), lines.size());
		boolean[] columnHasGalaxy = new boolean[universe.initialWidth];
		universe.rowOffsets[0] = 0;
		universe.columnOffsets[0] = 0;

		for (int r = 0; r < lines.size(); ++r) {
			String line = lines.get(r);
			if (line.length() != universe.initialWidth) {
				throw new RuntimeException("Inconsistent universe rows.");
			}

			boolean rowHasGalaxy = false;
			for (int c = 0; c < line.length(); ++c) {
				char location = line.charAt(c);
				if (location == '#') {
					Galaxy galaxy = new Galaxy(universe, r, c);
					universe.galaxies.add(galaxy);

					rowHasGalaxy = true;
					columnHasGalaxy[c] = true;
				}
			}

			if (r < lines.size() - 1) {
				if (!rowHasGalaxy) {
					universe.rowOffsets[r + 1] = universe.rowOffsets[r] + 1;
				} else {
					universe.rowOffsets[r + 1] = universe.rowOffsets[r];
				}
			}
		}

		for (int c = 0; c < universe.initialWidth - 1; ++c) {
			if (!columnHasGalaxy[c]) {
				universe.columnOffsets[c + 1] = universe.columnOffsets[c] + 1;
			} else {
				universe.columnOffsets[c + 1] = universe.columnOffsets[c];
			}
		}

		return universe;
	}

	public int getRowOffset(int row) {
		return rowOffsets[row] * (expansionFactor - 1);
	}

	public int getColumnOffset(int column) {
		return columnOffsets[column] * (expansionFactor - 1);
	}

	@Override
	public String toString() {
		return "Universe [initialWidth=" + initialWidth + ", initialHeight=" + initialHeight + ", expansionFactor="
				+ expansionFactor + ", galaxies=" + (galaxies.size() > 10 ? galaxies.size() : galaxies)
				+ ", rowOffsets=" + Arrays.toString(rowOffsets) + ", columnOffsets=" + Arrays.toString(columnOffsets)
				+ "]";
	}

}
