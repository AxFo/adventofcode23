package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import de.forsch.axel.adventofcode23.day12.Combinator;

public class Day12 {

	public static final boolean part1 = false;

	public static void main(String[] args) throws FileNotFoundException, IOException {

		Combinator.total_count = 0;
		long sumOfCombintations = 0;
		int row = 1;

		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day12.input"))) {
			String line;

			while ((line = reader.readLine()) != null) {
				String tokens[] = line.split(" ");

				int[] groups = Arrays.stream(tokens[1].split(",")).mapToInt(Integer::parseInt).toArray();
				String sequence = tokens[0].strip();
				sequence = sequence.replaceAll("\\.+", ".");

				if (!part1) {
					groups = Day12.unfold(5, groups);
					sequence = Day12.unfold(5, sequence);
				} else {
					System.out.println(sequence);
				}

				Combinator combinator = new Combinator();
				long start = System.currentTimeMillis();
//				combinator.findAllValidAssignments(sequence, groups);
//				long numCombinations = combinator.getNumAssignments();
				long numCombinations = combinator.findNumValidAssignmentsRecursive(sequence, groups);
				long t1 = System.currentTimeMillis() - start;
				sumOfCombintations += numCombinations;

				System.out.println(String.format(
						"row %-4d done in %7.3f seconds, checked %10d combinations, found %8d valid combinations (ratio: %9.6f)",
						row++, t1 / 1000.0, Combinator.count, numCombinations,
						(double) numCombinations / Combinator.count));
//				break;
			}
		}

		System.out.println("-------------------------------------------------");
		System.out.println(sumOfCombintations);
		System.out.println("total combinations checked: " + Combinator.total_count);
	}

	public static int[] unfold(int repeat, int[] groups) {
		int[] unfolded = new int[groups.length * 5];
		for (int r = 0; r < repeat; ++r) {
			for (int i = 0; i < groups.length; ++i) {
				unfolded[r * groups.length + i] = groups[i];
			}
		}
		return unfolded;
	}

	public static String unfold(int repeat, String sequence) {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < repeat; ++r) {
			sb.append(sequence);
			sb.append("?");
		}
		return sb.substring(0, sb.length() - 1);
	}
}
