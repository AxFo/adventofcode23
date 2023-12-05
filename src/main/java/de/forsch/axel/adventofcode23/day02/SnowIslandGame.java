package de.forsch.axel.adventofcode23.day02;

import java.util.ArrayList;
import java.util.List;

public class SnowIslandGame {

	public final int id;
	private List<SnowIslandGameSet> sets;

	public SnowIslandGame(int id) {
		this.id = id;
		this.sets = new ArrayList<>();
	}

	public void addGameSet(SnowIslandGameSet set) {
		this.sets.add(set);
	}

	public boolean isFeasible(int red, int green, int blue) {
		for (SnowIslandGameSet set : sets) {
			if (!set.isFeasible(red, green, blue)) {
				return false;
			}
		}
		return true;
	}

	public int power() {
		return red() * green() * blue();
	}

	public int red() {
		return sets.stream().max((a, b) -> a.red - b.red).get().red;
	}

	public int green() {
		return sets.stream().max((a, b) -> a.green - b.green).get().green;
	}

	public int blue() {
		return sets.stream().max((a, b) -> a.blue - b.blue).get().blue;
	}

	@Override
	public String toString() {
		return "SIG [id=" + id + ", sets=" + sets + "]";
	}
}
