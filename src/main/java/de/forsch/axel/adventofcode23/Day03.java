package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import de.forsch.axel.adventofcode23.day03.EngineSchematic;

public class Day03 {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		EngineSchematic schematic = new EngineSchematic();

		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day03.input"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				schematic.addRow(line);
			}
		}

		schematic.finalize();
		schematic.printNumAdjacentPartNumbers();

		System.out.println(schematic.sumOfPartNumbers());
		System.out.println(schematic.sumOfGearRatios());
	}
}
