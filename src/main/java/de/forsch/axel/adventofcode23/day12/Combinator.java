package de.forsch.axel.adventofcode23.day12;

import java.util.ArrayList;
import java.util.List;

import de.forsch.axel.adventofcode23.Day12;

public class Combinator {

	public static long count;
	public static long total_count;

	private List<Assignment> assignments;

	public Combinator() {
		this.assignments = new ArrayList<>();
		this.assignments.add(new Assignment());
	}

	public long findNumValidAssignmentsRecursive(String sequence, int[] groups) {
		Combinator.count = 0;
		return recurse(assignments.get(0), sequence, groups, 0);
	}

	private long recurse(Assignment assignment, String sequence, int[] groups, int depth) {
		if (depth == sequence.length()) {
			assignment.finalize();
			if (assignment.isValid(groups, -1, 0)) {
				return 1;
			}
			return 0;
		}
		Combinator.count++;
		Combinator.total_count++;
		char position = sequence.charAt(depth);
		int remaining = sequence.length() - depth - 1;

		int remainingHashtagLocations = (int) sequence.substring(depth).chars().filter(ch -> ch == '#' || ch == '?')
				.count();

		if (Day12.part1) {
			System.out.println(assignment);
		}

		Assignment next;
		int sum = 0;
		if (position == '?' || position == '.') {
			next = assignment.append(false);
			if (next != null && next.isValid(groups, remaining, remainingHashtagLocations)) {
				sum += recurse(next, sequence, groups, depth + 1);
			}
		}
		if (position == '?' || position == '#') {
			next = assignment.append(true);
			if (next != null && next.isValid(groups, remaining, remainingHashtagLocations)) {
				sum += recurse(next, sequence, groups, depth + 1);
			}
		}
		return sum;
	}

//	public void findAllValidAssignments(String sequence, int[] groups) {
//		String gr = "";
//		if (Day12.part1) {
//			for (int g : groups) {
//				gr += g + " ";
//			}
//			System.out.println(sequence);
//		}
//
//		int count = 0;
//
//		for (int pos = 0; pos < sequence.length(); ++pos) {
//			char position = sequence.charAt(pos);
//
//			int remaining = sequence.length() - pos - 1;
//
//			Assignment next;
//			List<Assignment> nextAssignments = new ArrayList<>();
//			for (Assignment assignment : assignments) {
//				next = assignment.append(false);
//				if (next != null && (position == '?' || position == '.') && next.isValid(groups, remaining)) {
//					nextAssignments.add(next);
//				}
//				next = assignment.append(true);
//				if (next != null && (position == '?' || position == '#') && next.isValid(groups, remaining)) {
//					nextAssignments.add(next);
//				}
//			}
//			assignments = nextAssignments;
//			if (Day12.part1) {
//				count += assignments.size();
//				System.out.println(gr + " -- " + count + " -- " + assignments.size() + " -- " + assignments);
//			}
//		}
//		assignments.stream().forEach(a -> a.finalize());
//		assignments = assignments.stream().filter(a -> a.isValid(groups, -1)).collect(Collectors.toList());
//		if (Day12.part1) {
//			System.out.println("finalized: " + assignments.size() + " -- " + assignments);
//		}
//	}

	@Override
	public String toString() {
		return "Combinator [assignments=" + assignments + "]";
	}

	public int getNumAssignments() {
		return assignments.size();
	}
}
