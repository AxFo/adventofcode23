package de.forsch.axel.adventofcode23.day15;

import java.util.LinkedList;

public class Box {

	public final int id;
	private LinkedList<String> labels;
	private LinkedList<Lens> lenses;

	public Box(int id) {
		this.id = id;
		this.labels = new LinkedList<>();
		this.lenses = new LinkedList<>();
	}

	public int getIdOfLabel(String label) {
		return labels.indexOf(label);

	}

	public void removeLabel(String label) {
		int i = getIdOfLabel(label);
		if (i != -1) {
			labels.remove(i);
			lenses.remove(i);
		}
	}

	public void addLens(Lens lens) {
		int i = getIdOfLabel(lens.label);
		if (i != -1) {
			lenses.set(i, lens);
		} else {
			labels.add(lens.label);
			lenses.add(lens);
		}
		lens.setBox(this);
	}

	public int getTotalFocussingPower() {
		int sum = 0;
		for (Lens lens : lenses) {
			sum += lens.focusingPower();
		}
		return sum;
	}

	@Override
	public String toString() {
		return "Box [id=" + id + ", labels=" + labels + ", lenses=" + lenses + ", getTotalFocussingPower()="
				+ getTotalFocussingPower() + "]";
	}

	public boolean isEmpty() {
		return labels.isEmpty();
	}
}
