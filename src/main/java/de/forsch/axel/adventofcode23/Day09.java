package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Day09 {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		int sumBackwards = 0;
		int sumForwards = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/day09.input"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				int[] numbers = Arrays.stream(line.strip().split(" ")).mapToInt(Integer::parseInt).toArray();
				int[] extrapolated = Day09.extrapolate(numbers);

				sumBackwards += extrapolated[0];
				sumForwards += extrapolated[1];
			}
		}

		System.out.println("===========================");
		System.out.println(String.format(" %10d --- %-10d", sumBackwards, sumForwards));
	}

	public static int[] extrapolate(int[] numbers) {
		int forwardsExtrapolated = 0;
		int backwardsExtrapolated = 0;

		int[] prevDiff = numbers;
		int[] currDiff;

		boolean allZero;
		for (int i = 0; i < numbers.length; ++i) {
			forwardsExtrapolated += prevDiff[prevDiff.length - 1];
			if (i % 2 == 0) {
				backwardsExtrapolated += prevDiff[0];
			} else {
				backwardsExtrapolated -= prevDiff[0];
			}

			allZero = true;
			currDiff = new int[prevDiff.length - 1];

			for (int j = 0; j < currDiff.length; ++j) {
				currDiff[j] = prevDiff[j + 1] - prevDiff[j];
				if (currDiff[j] != 0) {
					allZero = false;
				}
			}

			if (allZero) {
				System.out.println(String.format(" %10d ... %-10d", backwardsExtrapolated, forwardsExtrapolated));
				break;
			}
			prevDiff = currDiff;
		}
		return new int[] { backwardsExtrapolated, forwardsExtrapolated };
	}
}
