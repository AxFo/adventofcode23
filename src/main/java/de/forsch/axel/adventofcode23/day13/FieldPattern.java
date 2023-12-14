package de.forsch.axel.adventofcode23.day13;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FieldPattern {

	public final int height;
	public final int width;

	List<String> rows;
	List<String> columns;

	public FieldPattern(List<String> lines) {
		this.height = lines.size();
		this.width = lines.get(0).length();

		this.rows = lines;
		this.columns = initializeColumns(lines);
	}

	private List<String> initializeColumns(List<String> lines) {
		List<StringBuilder> columns = new ArrayList<>();
		for (int c = 0; c < width; ++c) {
			columns.add(new StringBuilder());
		}

		for (int r = 0; r < height; ++r) {
			String line = lines.get(r);
			for (int c = 0; c < width; ++c) {
				columns.get(c).append(line.charAt(c));
			}
		}
		return columns.stream().map(c -> c.toString()).collect(Collectors.toList());
	}

	public int badness(String s1, String s2) {
		int badness = 0;
		for (int i = 0; i < s1.length(); ++i) {
			if (s1.charAt(i) != s2.charAt(i)) {
				badness++;
			}
		}
		return badness;
	}

	public int findSymmetry(int minBadness, int allowedBadness) {
		int rowSymmetry = findRowSymmetry(minBadness, allowedBadness);
		if (rowSymmetry != -1) {
			return rowSymmetry * 100;
		}
		return findColumnSymmetry(minBadness, allowedBadness);
	}

	private int findRowSymmetry(int minBadness, int allowedBadness) {
		for (int r = 0; r < height - 1; ++r) {
			int badness;
			if ((badness = badness(rows.get(r), rows.get(r + 1))) <= allowedBadness) {
				for (int r3 = r - 1, r4 = r + 2; r3 >= 0 && r4 < height; --r3, ++r4) {
					badness += badness(rows.get(r3), rows.get(r4));
					if (badness > allowedBadness) {
						break;
					}
				}
				if (badness >= minBadness && badness <= allowedBadness) {
					return r + 1;
				}
			}
		}
		return -1;
	}

	private int findColumnSymmetry(int minBadness, int allowedBadness) {
		for (int c = 0; c < width - 1; ++c) {
			int badness;
			if ((badness = badness(columns.get(c), columns.get(c + 1))) <= allowedBadness) {
				for (int c3 = c - 1, c4 = c + 2; c3 >= 0 && c4 < width; --c3, ++c4) {
					badness += badness(columns.get(c3), columns.get(c4));
					if (badness > allowedBadness) {
						break;
					}
				}
				if (badness >= minBadness && badness <= allowedBadness) {
					return c + 1;
				}
			}
		}
		return -1;
	}
}
