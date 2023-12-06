package de.forsch.axel.adventofcode23.day06;

import java.util.function.Function;

public class BoatRace implements Function<Long, Long> {

	final long duration;
	final long record;

	public BoatRace(long duration, long record) {
		super();
		this.duration = duration;
		this.record = record;
	}

	public int[] getPossibleWinDurationInterval() {
		double low = duration / 2.0 - Math.sqrt(Math.pow(duration, 2) / 4 - record);
		double upp = duration / 2.0 + Math.sqrt(Math.pow(duration, 2) / 4 - record);

		// in case that low is exactly a border, skip it
		if (low % 1 == 0) {
			low++;
		}
		if (upp % 1 == 0) {
			upp--;
		}

		return new int[] { (int) Math.ceil(low), (int) Math.floor(upp) };
	}

	public int getNumberOfPossibleWinDurations() {
		int[] possibleWinDurationInterval = getPossibleWinDurationInterval();
		return possibleWinDurationInterval[1] - possibleWinDurationInterval[0] + 1;
	}

	public boolean isWinning(long buttonPressDuration) {
		return apply(buttonPressDuration) > record;
	}

	@Override
	public Long apply(Long buttonPressDuration) {
		return buttonPressDuration * (duration - buttonPressDuration);
	}
}
