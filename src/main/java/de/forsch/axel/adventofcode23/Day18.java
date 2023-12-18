package de.forsch.axel.adventofcode23;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.forsch.axel.adventofcode23.day18.Lagoon;

public class Day18 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/day18.input"), StandardCharsets.UTF_8);

		Lagoon lagoon = new Lagoon(lines, true);
		System.out.println(lagoon);
//		lagoon.printTrenchMap();
//		System.out.println();
		System.out.println(lagoon.fillOccupiedMap());
//		lagoon.printOccupied();
	}
}
