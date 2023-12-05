package de.forsch.axel.adventofcode23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.forsch.axel.adventofcode23.day02.SnowIslandGame;

public class Day02Test {

	@ParameterizedTest
	@MethodSource("getExamplePart")
	public void testPart1(String input, boolean expected, int red, int green, int blue) {
		assertEquals(Day02.parseGame(input).isFeasible(12, 13, 14), expected);
	}

	@ParameterizedTest
	@MethodSource("getExamplePart")
	public void testPart2(String input, boolean expected, int red, int green, int blue) {
		SnowIslandGame game = Day02.parseGame(input);
		assertEquals(game.red(), red);
		assertEquals(game.green(), green);
		assertEquals(game.blue(), blue);
	}

	private static Stream<Arguments> getExamplePart() {
		List<Arguments> args = new ArrayList<>();
		args.add(Arguments.of("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green", true, 4, 2, 6));
		args.add(Arguments.of("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue", true, 1, 3, 4));
		args.add(Arguments.of("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red", false, 20, 13,
				6));
		args.add(Arguments.of("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red", false, 14, 3,
				15));
		args.add(Arguments.of("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green", true, 6, 3, 2));
		return args.stream();
	}

}
