package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import de.forsch.axel.adventofcode23.day02.SnowIslandGame;
import de.forsch.axel.adventofcode23.day02.SnowIslandGameSet;

public class Day02 {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		int sumOfFeasibleGameIds = 0;
		int sumOfPowers = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day02.input"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				SnowIslandGame game = Day02.parseGame(line);

				if (game.isFeasible(12, 13, 14)) {
					sumOfFeasibleGameIds += game.id;
				}
				sumOfPowers += game.power();

				System.out.println(game + " -> " + game.isFeasible(12, 13, 14) + ", " + game.power());
			}
		}

		System.out.println("sumOfFeasibleGameIds: " + sumOfFeasibleGameIds);
		System.out.println("sumOfPowers: " + sumOfPowers);
	}

	public static SnowIslandGame parseGame(String line) {
		String[] tokens = line.split(":");
		int gameId = Integer.parseInt(tokens[0].substring(5));

		SnowIslandGame game = new SnowIslandGame(gameId);

		String[] sets = tokens[1].replaceAll(" red", "r").replaceAll(" green", "g").replaceAll(" blue", "b").split(";");
		for (String set : sets) {
			int r = 0;
			int g = 0;
			int b = 0;

			String[] draws = set.split(",");
			for (String draw : draws) {
				draw = draw.strip();
				if (draw.endsWith("r")) {
					r = Integer.parseInt(draw.substring(0, draw.length() - 1));
				} else if (draw.endsWith("g")) {
					g = Integer.parseInt(draw.substring(0, draw.length() - 1));
				} else if (draw.endsWith("b")) {
					b = Integer.parseInt(draw.substring(0, draw.length() - 1));
				} else {
					throw new RuntimeException();
				}
			}

			SnowIslandGameSet theSet = new SnowIslandGameSet(r, g, b);
			game.addGameSet(theSet);
		}

		return game;
	}

}
