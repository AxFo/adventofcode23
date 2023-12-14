package de.forsch.axel.adventofcode23.day14;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RollingPlatform {

	private final List<String> platform;

	public RollingPlatform(List<String> platform) {
		this.platform = platform;
	}

	public RollingPlatform rotateAntiClockwise() {
		List<StringBuilder> newPlatform = new ArrayList<>();
		for (int c = 0; c < width(); ++c) {
			newPlatform.add(new StringBuilder());
		}
		for (int r = 0; r < height(); ++r) {
			for (int c = 0; c < width(); ++c) {
				newPlatform.get(height() - c - 1).append(platform.get(r).charAt(c));
			}
		}
		return new RollingPlatform(newPlatform.stream().map(sb -> sb.toString()).collect(Collectors.toList()));
	}

	public RollingPlatform rotateClockwise() {
		List<StringBuilder> newPlatform = new ArrayList<>();
		for (int c = 0; c < width(); ++c) {
			newPlatform.add(new StringBuilder());
		}
		for (int r = height() - 1; r >= 0; --r) {
			for (int c = 0; c < width(); ++c) {
				newPlatform.get(c).append(platform.get(r).charAt(c));
			}
		}
		return new RollingPlatform(newPlatform.stream().map(sb -> sb.toString()).collect(Collectors.toList()));
	}

	public RollingPlatform rollUp() {
		List<StringBuilder> newPlatform = new ArrayList<>();
		for (int r = 0; r < height(); ++r) {
			newPlatform.add(new StringBuilder(platform.get(r)));
		}

		int[] lastObstacle = new int[width()];
		for (int r = 0; r < height(); ++r) {
			String line = platform.get(r);
			for (int c = 0; c < width(); ++c) {
				char ch = line.charAt(c);

				if (ch == '.') {
					continue;
				}
				if (ch == '#') {
					lastObstacle[c] = r + 1;
				}
				if (ch == 'O') {
					if (newPlatform.get(lastObstacle[c]).charAt(c) != 'O') {
						newPlatform.get(lastObstacle[c]).setCharAt(c, 'O');
						newPlatform.get(r).setCharAt(c, '.');
					} else {
						assert r == lastObstacle[c];
					}
					lastObstacle[c] += 1;
				}
			}
		}
		return new RollingPlatform(newPlatform.stream().map(sb -> sb.toString()).collect(Collectors.toList()));
	}

	public RollingPlatform cycle() {
		RollingPlatform newPlatform = this;
		for (int i = 0; i < 4; ++i) {
			newPlatform = newPlatform.rollUp().rotateClockwise();
		}
		return newPlatform;
	}

	public int getLoad() {
		int load = 0;
		for (int r = 0; r < height(); ++r) {
			String line = platform.get(r);
			for (int c = 0; c < width(); ++c) {
				char ch = line.charAt(c);
				if (ch == 'O') {
					load += height() - r;
				}
			}
		}
		return load;
	}

	public int height() {
		return platform.size();
	}

	public int width() {
		return platform.get(0).length();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String line : platform) {
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
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
		RollingPlatform rhs = (RollingPlatform) obj;
		return new EqualsBuilder()//
				.append(platform, rhs.platform)//
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 45)//
				.append(platform)//
				.toHashCode();
	}
}
