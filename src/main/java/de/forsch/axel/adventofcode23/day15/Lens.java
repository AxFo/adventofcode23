package de.forsch.axel.adventofcode23.day15;

public class Lens {

	public final String label;
	public final int focalLength;

	private Box box;

	public Lens(String label, int focalLength) {
		super();
		this.label = label;
		this.focalLength = focalLength;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public int focusingPower() {
		if (box == null) {
			return 0;
		}
		return (1 + box.id) * (1 + box.getIdOfLabel(label)) * focalLength;
	}

	@Override
	public String toString() {
		return "Lens [label=" + label + ", focalLength=" + focalLength + ", box=" + (box != null ? box.id : "null")
				+ ", focusingPower()=" + focusingPower() + "]";
	}

}
