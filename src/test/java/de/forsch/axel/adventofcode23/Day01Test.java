package de.forsch.axel.adventofcode23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Day01Test {

	@ParameterizedTest
	@MethodSource("getExamplePart1")
	public void testPart1(String input, int expected) {
		assertEquals(Day01.decode1(input), expected);
	}

	private static Stream<Arguments> getExamplePart1() {
		List<Arguments> args = new ArrayList<>();
		args.add(Arguments.of("1abc2", 12));
		args.add(Arguments.of("pqr3stu8vwx", 38));
		args.add(Arguments.of("a1b2c3d4e5f", 15));
		args.add(Arguments.of("treb7uchet", 77));
		return args.stream();
	}

	@ParameterizedTest
	@MethodSource("getExamplePart2")
	public void testPart2(String input, int expected) {
		assertEquals(Day01.decode2(input), expected);
	}

	private static Stream<Arguments> getExamplePart2() {
		List<Arguments> args = new ArrayList<>();
		args.add(Arguments.of("two1nine", 29));
		args.add(Arguments.of("eightwothree", 83));
		args.add(Arguments.of("abcone2threexyz", 13));
		args.add(Arguments.of("xtwone3four", 24));
		args.add(Arguments.of("4nineeightseven2", 42));
		args.add(Arguments.of("zoneight234", 14));
		args.add(Arguments.of("7pqrstsixteen", 76));
		return args.stream();
	}

}
