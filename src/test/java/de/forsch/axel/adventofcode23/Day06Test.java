package de.forsch.axel.adventofcode23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.forsch.axel.adventofcode23.day06.BoatRace;

public class Day06Test {

	@ParameterizedTest
	@MethodSource("getExamplePart")
	public void testPart1(long duration, long record, int numberOfWinDurations) {
		BoatRace race = new BoatRace(duration, record);
		assertEquals(numberOfWinDurations, race.getNumberOfPossibleWinDurations());
	}

	@ParameterizedTest
	@MethodSource("getPossibleDurationsExample")
	public void testPossibleDurations(BoatRace race, int[] possibleWinningDurationInterval) {
		int[] actualWinning = race.getPossibleWinDurationInterval();
		assertEquals(possibleWinningDurationInterval[0], actualWinning[0]);
		assertEquals(possibleWinningDurationInterval[1], actualWinning[1]);

	}

	private static Stream<Arguments> getExamplePart() {
		List<Arguments> args = new ArrayList<>();
		args.add(Arguments.of(7, 9, 4));
		args.add(Arguments.of(15, 40, 8));
		args.add(Arguments.of(30, 200, 9));
		args.add(Arguments.of(71530, 940200, 71503));
		return args.stream();
	}

	public static Stream<Arguments> getPossibleDurationsExample() {
		BoatRace race = new BoatRace(7, 9);
		List<Arguments> args = new ArrayList<>();
		args.add(Arguments.of(race, new int[] { 2, 5 }));
		return args.stream();
	}
}
