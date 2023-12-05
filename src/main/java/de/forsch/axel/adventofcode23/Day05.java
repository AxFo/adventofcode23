package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import de.forsch.axel.adventofcode23.day05.Almanac;
import de.forsch.axel.adventofcode23.day05.AlmanacMapping;

public class Day05 {
	public static void main(String[] args) throws FileNotFoundException, IOException {

		Almanac almanac;

		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day05.input"))) {
			String line = reader.readLine();

			almanac = new Almanac(Arrays.stream(line.split(":")[1]//
					.strip()//
					.split("\\s+"))//
					.mapToLong(Long::parseLong)//
					.toArray());

			reader.readLine(); // skip empty row

			AlmanacMapping currentAlmanacMapping = null;
			boolean nextIsMappingName = true;
			while ((line = reader.readLine()) != null) {
				if (nextIsMappingName) {
					String[] tokens = line.split("\\s+")[0].split("-");
					currentAlmanacMapping = new AlmanacMapping(tokens[0], tokens[2]);
					almanac.addMapping(currentAlmanacMapping);
					nextIsMappingName = false;
					continue;
				}

				if (line.isBlank()) {
					nextIsMappingName = true;
					continue;
				}

				long[] mapping = Arrays.stream(line.split("\\s+")).mapToLong(Long::parseLong).toArray();
				currentAlmanacMapping.addMappings(mapping[1], mapping[0], mapping[2]);
			}
		}

		long done = 0;
		long todo = almanac.numberOfSeedsToBePlanted2();
		double perc = 0;
		double loggingInterval = .01;

		System.out.println("number of seeds to be planted: " + todo);
		long start = System.currentTimeMillis();

		long lowestLocationNumber = Long.MAX_VALUE;
		for (Long seedId : almanac.getSeedsToBePlanted2()) {
			long mappedId = almanac.mapSeed(seedId);
			if (mappedId < lowestLocationNumber) {
				lowestLocationNumber = mappedId;
			}

			done++;
			if ((double) done / todo >= perc) {
				System.out.println((int) Math.round((double) done / todo * 100) + "% of seeds mapped");
				perc += loggingInterval;
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("time needed: " + (end - start) / 1000.0 + " seconds");

		System.out.println("minimum location id: " + lowestLocationNumber);
	}
}
