package de.forsch.axel.adventofcode23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.forsch.axel.adventofcode23.day04.Scratchcard;

public class Day04Test {

	@ParameterizedTest
	@MethodSource("getExamplePart1")
	public void testPart1(Scratchcard scratchcard, int points) {
		assertEquals(points, scratchcard.points());
	}

	private static Stream<Arguments> getExamplePart1() {
		List<Scratchcard> scratchcards = getScratchcards();
		List<Arguments> args = new ArrayList<>();
		args.add(Arguments.of(scratchcards.get(0), 8));
		args.add(Arguments.of(scratchcards.get(1), 2));
		args.add(Arguments.of(scratchcards.get(2), 2));
		args.add(Arguments.of(scratchcards.get(3), 1));
		args.add(Arguments.of(scratchcards.get(4), 0));
		args.add(Arguments.of(scratchcards.get(5), 0));
		return args.stream();
	}

	@ParameterizedTest
	@MethodSource("getExamplePart2")
	public void testPart2(List<Scratchcard> scratchcards, int numTotalScratchcards) {
		Day04.processScratchcards(scratchcards);
		assertEquals(numTotalScratchcards, scratchcards.stream().mapToInt(Scratchcard::count).sum());
	}

	private static Stream<Arguments> getExamplePart2() {
		List<Arguments> args = new ArrayList<>();
		args.add(Arguments.of(getScratchcards(), 30));
		return args.stream();
	}

	private static List<Scratchcard> getScratchcards() {
		String input = "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53\n"
				+ "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19\n"
				+ "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1\n"
				+ "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83\n"
				+ "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36\n"
				+ "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11";
		List<Scratchcard> scratchcards = new ArrayList<>();

		for (String token : input.split("\n")) {
			Scratchcard scratchcard = Day04.parseLine(token);
			scratchcards.add(scratchcard);
		}
		return scratchcards;
	}

}
