package de.forsch.axel.adventofcode23;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.forsch.axel.adventofcode23.day10.GridSearch;
import de.forsch.axel.adventofcode23.day10.PipeGrid;

public class Day10 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/test/resources/day10-6.input"), StandardCharsets.UTF_8);
//		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/day10.input"), StandardCharsets.UTF_8);

		PipeGrid grid = new PipeGrid(lines);

		GridSearch search = new GridSearch(grid);
		System.out.println(search.detectCycle(grid.getSource()));
		System.out.println(search.getEnclosedArea());
	}
}
