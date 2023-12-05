package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.forsch.axel.adventofcode23.day04.Scratchcard;

public class Day04 {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		List<Scratchcard> scratchcards = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day04.input"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				try {
					Scratchcard scratchcard = Day04.parseLine(line);
					scratchcards.add(scratchcard);
				} catch (NumberFormatException e) {
					System.err.println(line);
					throw e;
				}
			}
		}

		scratchcards.add(0, null); // to align game indices (they start with 1)
		for (int gameId = 1; gameId < scratchcards.size(); ++gameId) {
			Scratchcard scratchcard = scratchcards.get(gameId);
			int cardsWon = scratchcard.cardsWon();
			for (int idToDuplicate = gameId + 1; idToDuplicate < gameId + 1 + cardsWon
					&& idToDuplicate < scratchcards.size(); ++idToDuplicate) {
				scratchcards.get(idToDuplicate).addDuplicates(scratchcard.count());
			}
		}
		scratchcards.remove(0); // remove scratchcard that was used to align indices

		for (

		Scratchcard scratchcard : scratchcards) {
			System.out.println(scratchcard);
		}

		System.out.println("points = " + scratchcards.stream().mapToInt(Scratchcard::points).sum());
		System.out.println("number of cards = " + scratchcards.stream().mapToInt(Scratchcard::count).sum());
	}

	public static Scratchcard parseLine(String line) {
		String[] tokens = line.split(":");

		int id = Integer.parseInt(tokens[0].substring(5).strip());
		Scratchcard scratchcard = new Scratchcard(id);

		String[] numbers = tokens[1].split("\\|");

		for (String winningNumberString : numbers[0].strip().split("\\s+")) {
			int winningNumber = Integer.parseInt(winningNumberString.strip());
			scratchcard.addWinningNumber(winningNumber);
		}

		for (String myNumberString : numbers[1].strip().split("\\s+")) {
			int myNumber = Integer.parseInt(myNumberString.strip());
			scratchcard.addMyNumber(myNumber);
		}

		return scratchcard;
	}
}
