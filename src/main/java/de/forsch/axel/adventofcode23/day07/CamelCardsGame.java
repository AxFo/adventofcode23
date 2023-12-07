package de.forsch.axel.adventofcode23.day07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CamelCardsGame {

	private List<CamelCardsHand> hands;

	public CamelCardsGame() {
		this.hands = new ArrayList<>();
	}

	public void addHand(CamelCardsHand hand) {
		this.hands.add(hand);
	}

	public int evaluateWinnings(boolean ruleset1) {
		if (ruleset1) {
			Collections.sort(hands, RULESET_1_COMPARATOR);
		} else {
			Collections.sort(hands, RULESET_2_COMPARATOR);
		}

		int totalWinnings = 0;
		for (int i = 0; i < hands.size(); ++i) {
			totalWinnings += hands.get(i).bid * (i + 1);
		}
		return totalWinnings;
	}

	public static Comparator<CamelCardsHand> RULESET_1_COMPARATOR = new Comparator<>() {

		@Override
		public int compare(CamelCardsHand o1, CamelCardsHand o2) {
			if (o1.type1 != o2.type1) {
				return o1.type1.rank - o2.type1.rank;
			}
			for (int i = 0; i < 5; ++i) {
				if (o1.cards.charAt(i) != o2.cards.charAt(i)) {
					return CardRank.of(o1.cards.charAt(i)).rank - CardRank.of(o2.cards.charAt(i)).rank;
				}
			}
			return 0;
		}
	};

	public static Comparator<CamelCardsHand> RULESET_2_COMPARATOR = new Comparator<>() {

		@Override
		public int compare(CamelCardsHand o1, CamelCardsHand o2) {
			if (o1.type2 != o2.type2) {
				return o1.type2.rank - o2.type2.rank;
			}
			for (int i = 0; i < 5; ++i) {
				if (o1.cards.charAt(i) != o2.cards.charAt(i)) {
					if (o1.cards.charAt(i) == 'J') {
						return -1;
					}
					if (o2.cards.charAt(i) == 'J') {
						return 1;
					}
					return CardRank.of(o1.cards.charAt(i)).rank - CardRank.of(o2.cards.charAt(i)).rank;
				}
			}
			return 0;
		}
	};

}
