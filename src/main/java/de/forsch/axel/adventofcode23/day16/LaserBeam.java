package de.forsch.axel.adventofcode23.day16;

import de.forsch.axel.adventofcode23.notation.Direction;

public class LaserBeam {

	public final Contraption contraption;
	public final int row;
	public final int column;
	public final Direction direction;

	public LaserBeam(Contraption contraption, int row, int column, Direction direction) {
		super();
		this.direction = direction;
		this.row = row;
		this.column = column;
		this.contraption = contraption;
	}
}
