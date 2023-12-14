package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.forsch.axel.adventofcode23.day14.RollingPlatform;

public class Day14 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String inputFile = "src/main/resources/day14.input";
		System.out.println("part1: " + Day14.part1(inputFile));

		List<String> lines = Files.readAllLines(Paths.get(inputFile), StandardCharsets.UTF_8);

		RollingPlatform p = new RollingPlatform(lines);

		int numCycles = (int) 1e9;
		int cyclesComputed = 0;
		Map<RollingPlatform, Integer> platformMap = new HashMap<>();
		for (int i = 0; i < numCycles; ++i) {
			p = p.cycle();
			cyclesComputed++;
			if (platformMap.containsKey(p)) {
				// this could also be done analytically for better performance
				// additionally: the result configuration is already in the map, could be
				// searched directly
				int d = i - platformMap.get(p);
				while (i < numCycles - d) {
					i += d;
				}
			} else {
				platformMap.put(p, i);
			}
		}

		System.out.println("part2: " + p.getLoad());
		System.out.println("Cycles computed: " + cyclesComputed);
		System.out.println("Map size: " + platformMap.size());
	}

	public static int part1(String inputFile) throws FileNotFoundException, IOException {
		int sum = 0;
		int r = 0;
		int numRocks = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {

			String line;
			int[] lastObstacle = null;
			while ((line = reader.readLine()) != null) {
				r++;
				if (lastObstacle == null) {
					lastObstacle = new int[line.length()];
				}

				for (int i = 0; i < line.length(); ++i) {
					char c = line.charAt(i);

					if (c == '.') {
						continue;
					}
					if (c == '#') {
						lastObstacle[i] = r;
					}
					if (c == 'O') {
						sum += lastObstacle[i];
						lastObstacle[i] += 1;
						numRocks++;
					}
				}
			}
		}
		return (numRocks * r - sum);
	}
}
