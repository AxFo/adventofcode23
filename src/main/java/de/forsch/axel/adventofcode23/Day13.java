package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.forsch.axel.adventofcode23.day13.FieldPattern;

public class Day13 {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		int sumOfNotes1 = 0;
		int sumOfNotes2 = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day13.input"))) {
			String line;

			List<String> input = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				if (line.isBlank()) {
					FieldPattern pattern = new FieldPattern(input);
					int p1 = pattern.findSymmetry(0, 0);
					int p2 = pattern.findSymmetry(1, 1);
					System.out.println(p1 + " " + p2);
					sumOfNotes1 += p1;
					sumOfNotes2 += p2;
					input = new ArrayList<>();
					continue;
				}
				input.add(line);
			}
		}

		System.out.println("part1: " + sumOfNotes1);
		System.out.println("part2: " + sumOfNotes2);
	}
}
