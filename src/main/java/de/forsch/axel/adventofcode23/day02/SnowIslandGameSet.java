package de.forsch.axel.adventofcode23.day02;

public final class SnowIslandGameSet {

	public final int red;
	public final int green;
	public final int blue;

	public SnowIslandGameSet(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public boolean isFeasible(int red, int green, int blue) {
		if (this.red > red) {
			return false;
		}
		if (this.green > green) {
			return false;
		}
		if (this.blue > blue) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SIGS [red=" + red + ", green=" + green + ", blue=" + blue + "]";
	}

}
