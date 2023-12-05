package de.forsch.axel.adventofcode23.day04;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Scratchcard {

	public final int id;
	private int count;

	private Set<Integer> winningNumbers;
	private Set<Integer> myNumbers;
	private Set<Integer> myWinningNumbers;

	public Scratchcard(int id) {
		this.id = id;
		this.count = 1;
		this.winningNumbers = new TreeSet<>();
		this.myNumbers = new TreeSet<>();
	}

	public void addDuplicate() {
		this.addDuplicates(1);
	}

	public void addDuplicates(int numNewDuplicates) {
		this.count += numNewDuplicates;
	}

	public int count() {
		return count;
	}

	public boolean addWinningNumber(int winningNumber) {
		return this.winningNumbers.add(winningNumber);
	}

	public boolean addMyNumber(int myNumber) {
		return this.myNumbers.add(myNumber);
	}

	public int points() {
		if (myWinningNumbers == null) {
			myWinningNumbers = myNumbers.stream().filter(i -> winningNumbers.contains(i)).collect(Collectors.toSet());
		}
		int count = myWinningNumbers.size();
		return (int) Math.pow(2, count - 1);
	}

	public int cardsWon() {
		if (myWinningNumbers == null) {
			myWinningNumbers = myNumbers.stream().filter(i -> winningNumbers.contains(i)).collect(Collectors.toSet());
		}
		return myWinningNumbers.size();
	}

	@Override
	public String toString() {
		return "Scratchcard [points=" + points() + ", count=" + count + ", myWinningNumbers=" + myWinningNumbers
				+ ", id=" + id + ", winningNumbers=" + winningNumbers + ", myNumbers=" + myNumbers + "]";
	}
}
