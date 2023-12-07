package de.forsch.axel.adventofcode23;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.forsch.axel.adventofcode23.day07.CamelCardsGame;
import de.forsch.axel.adventofcode23.day07.CamelCardsHand;

public class Day07 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("src/main/resources/day07.input"), StandardCharsets.UTF_8);

		CamelCardsGame game = new CamelCardsGame();

		for (String line : lines) {
			String[] tokens = line.strip().split(" ");
			String cards = tokens[0];
			int bid = Integer.parseInt(tokens[1]);

			CamelCardsHand hand = new CamelCardsHand(cards, bid);
			game.addHand(hand);
		}

		System.out.println("Total winnings (rule 1): " + game.evaluateWinnings(true));
		System.out.println("Total winnings (rule 2): " + game.evaluateWinnings(false));
	}

}
