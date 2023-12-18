package de.forsch.axel.adventofcode23;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.forsch.axel.adventofcode23.day16.Contraption;
import de.forsch.axel.adventofcode23.notation.Direction;

public class Day16 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day16.input"), StandardCharsets.UTF_8);

		Contraption contraption = new Contraption(lines, 0, 0, Direction.RIGHT);
		contraption.run();
		contraption.printEnergized();
		System.out.println("part1 = " + contraption.energy());

		int w = contraption.width;
		int h = contraption.height;
		int max = 0;

		for (int r = 0; r < h; ++r) {
			max = Math.max(max, new Contraption(lines, r, 0, Direction.RIGHT).run().energy());
			max = Math.max(max, new Contraption(lines, r, w - 1, Direction.LEFT).run().energy());
			if (r == 0) {
				max = Math.max(max, new Contraption(lines, r, 0, Direction.DOWN).run().energy());
				max = Math.max(max, new Contraption(lines, r, w - 1, Direction.DOWN).run().energy());
			}
			if (r == h - 1) {
				max = Math.max(max, new Contraption(lines, r, 0, Direction.UP).run().energy());
				max = Math.max(max, new Contraption(lines, r, w - 1, Direction.UP).run().energy());
			}
		}

		for (int c = 0; c < w; ++c) {
			max = Math.max(max, new Contraption(lines, 0, c, Direction.DOWN).run().energy());
			max = Math.max(max, new Contraption(lines, h - 1, c, Direction.UP).run().energy());
			if (c == 0) {
				max = Math.max(max, new Contraption(lines, 0, c, Direction.RIGHT).run().energy());
				max = Math.max(max, new Contraption(lines, h - 1, c, Direction.RIGHT).run().energy());
			}
			if (c == w - 1) {
				max = Math.max(max, new Contraption(lines, 0, c, Direction.LEFT).run().energy());
				max = Math.max(max, new Contraption(lines, h - 1, c, Direction.LEFT).run().energy());
			}
		}
		System.out.println("part2 = " + max);
	}
}
