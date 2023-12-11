package de.forsch.axel.adventofcode23;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.forsch.axel.adventofcode23.day11.Universe;

public class Day11 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day11.input"), StandardCharsets.UTF_8);

		Universe universe = Universe.parse(lines);

		System.out.println(universe);
		System.out.println("part1: " + universe.sumOfDistances());

		universe.setExpansionFactor(1000000);
		System.out.println(universe);
		System.out.println("part2: " + universe.sumOfDistances());
	}
}
