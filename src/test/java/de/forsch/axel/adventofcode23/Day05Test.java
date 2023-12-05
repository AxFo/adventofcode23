package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.forsch.axel.adventofcode23.day05.Almanac;

public class Day05Test {

	@ParameterizedTest
	@MethodSource("getExamplePart")
	public void testPart1(Almanac almanac, int minLocation1, int minLocation2) {
		// TODO
	}

	private static Stream<Arguments> getExamplePart() throws FileNotFoundException, IOException {
		String input = "seeds: 79 14 55 13\n" + "\n" + "seed-to-soil map:\n" + "50 98 2\n" + "52 50 48\n" + "\n"
				+ "soil-to-fertilizer map:\n" + "0 15 37\n" + "37 52 2\n" + "39 0 15\n" + "\n"
				+ "fertilizer-to-water map:\n" + "49 53 8\n" + "0 11 42\n" + "42 0 7\n" + "57 7 4\n" + "\n"
				+ "water-to-light map:\n" + "88 18 7\n" + "18 25 70\n" + "\n" + "light-to-temperature map:\n"
				+ "45 77 23\n" + "81 45 19\n" + "68 64 13\n" + "\n" + "temperature-to-humidity map:\n" + "0 69 1\n"
				+ "1 0 69\n" + "\n" + "humidity-to-location map:\n" + "60 56 37\n" + "56 93 4";
		Almanac almanac;
		try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
			almanac = Day05.parseAlmanac(reader);
		}

		List<Arguments> args = new ArrayList<>();
		args.add(Arguments.of(almanac, 35, 46));
		return args.stream();
	}
}
