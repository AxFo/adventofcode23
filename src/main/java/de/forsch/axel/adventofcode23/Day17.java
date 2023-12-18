package de.forsch.axel.adventofcode23;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.forsch.axel.adventofcode23.day17.HeatLossMap;
import de.forsch.axel.adventofcode23.notation.GridCoordinate;

public class Day17 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/day17.input"), StandardCharsets.UTF_8);

		HeatLossMap map = new HeatLossMap(lines);
		System.out.println(map);

		System.out.println(map.findPath(new GridCoordinate(0, 0), new GridCoordinate(map.height - 1, map.width - 1)));
	}
}
