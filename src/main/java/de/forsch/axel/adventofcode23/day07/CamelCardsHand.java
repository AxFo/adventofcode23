package de.forsch.axel.adventofcode23.day07;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CamelCardsHand {

	public final String cards;
	public final int bid;
	public final HandType type1;
	public final HandType type2;

	public CamelCardsHand(String cards, int bid) {
		if (cards.length() != 5) {
			throw new IllegalArgumentException(
					"A camel card hand must have 5 cards (characters). Got: " + cards.length());
		}
		this.cards = cards;
		this.bid = bid;
		this.type1 = type(true);
		this.type2 = type(false);
	}

	public HandType type(boolean ruleset1) {
		Set<Integer> chars = cards.chars().boxed().collect(Collectors.toSet());
		List<Integer> counts = new LinkedList<>();
		int countJokers = 0;
		for (int c : chars) {
			int count = (int) cards.chars().filter(ch -> ch == c).count();
			if (c == 'J' && !ruleset1) {
				countJokers = count;
			} else {
				counts.add(count);
			}
		}
		Collections.sort(counts);
		Collections.reverse(counts);

		if (!ruleset1) {
			if (counts.isEmpty()) { // all cards are jokers
				return HandType.FIVE_OF_A_KIND;
			}
			counts.set(0, counts.get(0) + countJokers);
		}

		if (counts.get(0) == 5) {
			return HandType.FIVE_OF_A_KIND;
		}
		if (counts.get(0) == 4) {
			return HandType.FOUR_OF_A_KIND;
		}
		if (counts.get(0) == 3) {
			if (counts.get(1) == 2) {
				return HandType.FULL_HOUSE;
			}
			return HandType.THREE_OF_A_KIND;
		}
		if (counts.get(0) == 2) {
			if (counts.get(1) == 2) {
				return HandType.TWO_PAIR;
			}
			return HandType.ONE_PAIR;
		}
		return HandType.HIGH_CARD;
	}

	@Override
	public String toString() {
		return "CamelCardsHand [cards=" + cards + ", bid=" + bid + ", type1=" + type1 + ", type2=" + type2 + "]";
	}
}
