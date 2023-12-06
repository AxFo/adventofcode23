package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.forsch.axel.adventofcode23.day06.BoatRace;

public class Day06 {

	private static final Pattern number = Pattern.compile("\\d+");

	private static final boolean part1 = false;

	public static void main(String[] args) throws FileNotFoundException, IOException {

		List<Long> durations = new ArrayList<>();
		List<Long> records = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/day06.input"))) {
			String line = reader.readLine();
			if (!part1) {
				line = line.replaceAll(" ", "");
			}

			Matcher m = number.matcher(line);
			while (m.find()) {
				durations.add(Long.parseLong(m.group()));
			}

			line = reader.readLine();
			if (!part1) {
				line = line.replaceAll(" ", "");
			}
			m = number.matcher(line);
			while (m.find()) {
				records.add(Long.parseLong(m.group()));
			}
		}

		if (durations.size() != records.size()) {
			throw new RuntimeException("Parsing failed.");
		}

		int durationProduct = 0;
		List<BoatRace> races = new ArrayList<>();
		for (int i = 0; i < durations.size(); ++i) {
			BoatRace race = new BoatRace(durations.get(i), records.get(i));
			if (durationProduct == 0) {
				durationProduct = race.getNumberOfPossibleWinDurations();
			} else {
				durationProduct *= race.getNumberOfPossibleWinDurations();
			}
			races.add(race);
		}

		System.out.println("Product: " + durationProduct);
	}
}
