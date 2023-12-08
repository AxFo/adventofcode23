package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.forsch.axel.adventofcode23.day08.Node;

public class Day08 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String instructions;
		HashMap<String, Node> nodeMap = new HashMap<>();
		HashMap<String, Node> sources = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day08.input"))) {
			instructions = reader.readLine();
			String line = reader.readLine(); // empty line

			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split(" ");

				String id = tokens[0];
				String leftId = tokens[2].substring(1, 4);
				String rightId = tokens[3].substring(0, 3);

				Node currentNode = nodeMap.get(id);
				if (currentNode == null) {
					currentNode = new Node(id);
					nodeMap.put(id, currentNode);
					if (id.endsWith("A")) {
						sources.put(id, currentNode);
					}
				}
				Node leftNode = nodeMap.get(leftId);
				if (leftNode == null) {
					leftNode = new Node(leftId);
					nodeMap.put(leftId, leftNode);
					if (leftId.endsWith("A")) {
						sources.put(leftId, leftNode);
					}
				}
				Node rightNode = nodeMap.get(rightId);
				if (rightNode == null) {
					rightNode = new Node(rightId);
					nodeMap.put(rightId, rightNode);
					if (rightId.endsWith("A")) {
						sources.put(rightId, rightNode);
					}
				}

				currentNode.setLeft(leftNode);
				currentNode.setRight(rightNode);

			}
		}

		System.out.println(part2(instructions, nodeMap, sources));
	}

	public static long part2(String instructions, HashMap<String, Node> nodeMap, HashMap<String, Node> sources) {
		HashSet<Integer> nodesToSearch = new HashSet<>();
		Node[] currentNodes = new Node[sources.size()];
		int i = 0;
		for (Node source : sources.values()) {
			currentNodes[i] = source;
			nodesToSearch.add(i);
			i++;
		}

		List<List<Integer>> hits = new ArrayList<>();
		List<Set<Node>> fullRevolutionNodes = new ArrayList<>();
		for (i = 0; i < sources.size(); ++i) {
			fullRevolutionNodes.add(new HashSet<>());
			hits.add(new ArrayList<>());
		}
		int[] fullRevolutionAfter = new int[sources.size()];

		i = 0;
		int count = 0;
		int numRevolutions = 0;
		while (!nodesToSearch.isEmpty()) {
			if (i == instructions.length()) {
				i = 0;
				numRevolutions++;
				Set<Integer> nodesToRemove = new HashSet<>();
				for (int j : nodesToSearch) {
					if (fullRevolutionAfter[j] == 0) {
						if (fullRevolutionNodes.get(j).contains(currentNodes[j])) {
							fullRevolutionAfter[j] = numRevolutions;
							nodesToRemove.add(j);
						} else {
							fullRevolutionNodes.get(j).add(currentNodes[j]);
						}
					}
				}
				nodesToSearch.removeAll(nodesToRemove);
			}
			count++;

			char c = instructions.charAt(i);
			for (int n : nodesToSearch) {
				if (c == 'L') {
					currentNodes[n] = currentNodes[n].getLeft();
				} else if (c == 'R') {
					currentNodes[n] = currentNodes[n].getRight();
				} else {
					throw new RuntimeException("unknown instruction");
				}
				if (currentNodes[n].id.endsWith("Z")) {
					hits.get(n).add(count);
				}
			}

			i++;
		}

		long[] result = new long[sources.size()];
		for (i = 0; i < sources.size(); ++i) {
			System.out.println(i + ": " + fullRevolutionAfter[i] + ", " + hits.get(i));
			result[i] = hits.get(i).get(0);
		}

		return lcm(result);
	}

	public static int part1(String instructions, HashMap<String, Node> nodeMap) {
		Node currentNode = nodeMap.get("AAA");
		int count = 0;
		int i = 0;
		while (!currentNode.id.equals("ZZZ")) {
			if (i == instructions.length()) {
				i = 0;
			}

			char c = instructions.charAt(i);
			if (c == 'L') {
				currentNode = currentNode.getLeft();
			} else if (c == 'R') {
				currentNode = currentNode.getRight();
			} else {
				throw new RuntimeException("unknown instruction");
			}

			i++;
			count++;
		}
		return count;
	}

	public static long lcm(long[] numbers) {
		long lcm = 1;
		int divisor = 2;

		while (true) {
			int counter = 0;
			boolean divisible = false;

			for (int i = 0; i < numbers.length; i++) {

				// lcm (n1, n2, ... 0) = 0.
				// For negative number we convert into positive and calculate lcm.
				if (numbers[i] == 0) {
					return 0;
				} else if (numbers[i] < 0) {
					numbers[i] = numbers[i] * (-1);
				}
				if (numbers[i] == 1) {
					counter++;
				}

				// Divide element_array by devisor if complete division i.e. without remainder
				// then replace number with quotient; used for find next factor
				if (numbers[i] % divisor == 0) {
					divisible = true;
					numbers[i] = numbers[i] / divisor;
				}
			}

			// If divisor able to completely divide any number from array multiply with lcm
			// and store into lcm and continue to same divisor for next factor finding. else
			// increment divisor
			if (divisible) {
				lcm = lcm * divisor;
			} else {
				divisor++;
			}

			// Check if all element_array is 1 indicate we found all factors and terminate
			// while loop.
			if (counter == numbers.length) {
				return lcm;
			}
		}
	}

}
