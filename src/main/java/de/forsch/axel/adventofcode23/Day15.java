package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.forsch.axel.adventofcode23.day15.Box;
import de.forsch.axel.adventofcode23.day15.Lens;

public class Day15 {

	public static boolean test = false;

	public static void main(String[] args) throws IOException {
		System.out.println("HASH = " + Day15.getHashCode("HASH"));

		String initializationSequence = test ? Day15.readFile("src/test/resources/day15.input")
				: Day15.readFile("src/main/resources/day15.input");
		String[] tokens = initializationSequence.split(",");

		System.out.println("part1 = " + Day15.part1(tokens));
		System.out.println("part2 = " + Day15.part2(tokens));

	}

	public static int part1(String[] tokens) {
		int sum = 0;
		for (String token : tokens) {
			int hashCode = Day15.getHashCode(token);
			sum += hashCode;
		}
		return sum;
	}

	public static int part2(String[] tokens) {
		List<Box> boxes = new ArrayList<>();
		for (int i = 0; i < 256; ++i) {
			boxes.add(new Box(i));
		}

		for (String token : tokens) {
			if (token.contains("-")) {
				String label = token.substring(0, token.length() - 1);
				int hashCode = Day15.getHashCode(label);

				boxes.get(hashCode).removeLabel(label);

				if (test) {
					System.out.println(token);
					boxes.stream().filter(b -> !b.isEmpty()).forEach(System.out::println);
					System.out.println();
				}
				continue;
			}
			if (token.contains("=")) {
				String[] s = token.split("=");
				String label = s[0];
				int value = Integer.parseInt(s[1]);
				int hashCode = Day15.getHashCode(label);

				boxes.get(hashCode).addLens(new Lens(label, value));

				if (test) {
					System.out.println(label + ", " + value);
					boxes.stream().filter(b -> !b.isEmpty()).forEach(System.out::println);
					System.out.println();
				}
				continue;
			}
			throw new RuntimeException();
		}

		return boxes.stream().mapToInt(Box::getTotalFocussingPower).sum();
	}

	public static int getHashCode(String s) {
		int currentValue = 0;
		for (char ch : s.toCharArray()) {
			currentValue += ch;
			currentValue *= 17;
			currentValue %= 256;
		}

		return currentValue;
	}

	public static String readFile(String filename) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String nextLine = "";
			StringBuffer sb = new StringBuffer();
			while ((nextLine = br.readLine()) != null) {
				sb.append(nextLine);
			}
			// remove newlines
			String newString = sb.toString().replaceAll("\n", "");

			return newString;
		}
	}
}
