package de.forsch.axel.adventofcode23;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.forsch.axel.adventofcode23.day03.EngineSchematic;

public class Day03Test {

	@ParameterizedTest
	@MethodSource("getExamplePart")
	public void testPart1(EngineSchematic schematic, int sumOfPartNumbers, int sumOfGearRatios) {
		assertEquals(sumOfPartNumbers, schematic.sumOfPartNumbers());
	}

	@ParameterizedTest
	@MethodSource("getExamplePart")
	public void testPart2(EngineSchematic schematic, int sumOfPartNumbers, int sumOfGearRatios) {
		assertEquals(sumOfGearRatios, schematic.sumOfGearRatios());
	}

	private static Stream<Arguments> getExamplePart() {
		String input = "467..114..\n" + "...*......\n" + "..35..633.\n" + "......#...\n" + "617*......\n"
				+ ".....+.58.\n" + "..592.....\n" + "......755.\n" + "...$.*....\n" + ".664.598..";
		EngineSchematic schematic = new EngineSchematic();
		for (String token : input.split("\n")) {
			schematic.addRow(token);
		}
		schematic.finalize();

		List<Arguments> args = new ArrayList<>();
		args.add(Arguments.of(schematic, 4361, 467835));
		return args.stream();
	}

}
