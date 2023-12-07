package de.forsch.axel.adventofcode23.day07;

public enum CardRank {

	A(13), K(12), Q(11), J(10), T(9), D9(8), D8(7), D7(6), D6(5), D5(4), D4(3), D3(2), D2(1);

	public final int rank;

	private CardRank(int rank) {
		this.rank = rank;
	}

	public static CardRank of(char c) {
		try {
			return CardRank.valueOf(String.valueOf(c));
		} catch (IllegalArgumentException e1) {
			try {
				return CardRank.valueOf("D" + c);
			} catch (IllegalArgumentException e2) {
				throw new RuntimeException(c + " is not a card rank.");
			}
		}
	}
}
