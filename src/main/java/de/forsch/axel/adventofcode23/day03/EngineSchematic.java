package de.forsch.axel.adventofcode23.day03;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EngineSchematic {

	private static Pattern symbols = Pattern.compile("[!@#$%^&\\*()_+\\-=\\[\\]{};':\"\\|,<>\\/?]");
	private static Pattern numbers = Pattern.compile("\\d+");
	private static Pattern potentialGear = Pattern.compile("\\*");

	private boolean isFinal = false;
	private int rowLength = -1;
	private List<String> rows;
	private boolean[][] adjacentToSymbol;
	private List<List<List<Integer>>> adjacentPartNumbers;

	public EngineSchematic() {
		this.rows = new ArrayList<>();
	}

	public void addRow(String newRow) {
		if (isFinal) {
			throw new RuntimeException("Already finalized.");
		}
		if (rowLength == -1) {
			rowLength = newRow.length();
			rows.add(newRow);
			return;
		}

		if (newRow.length() != rowLength) {
			throw new RuntimeException("Uneven row lengths.");
		}
		rows.add(newRow);
	}

	@Override
	public void finalize() {
		this.isFinal = true;
		this.fillAdjacentToSymbolMatrix();
		this.fillNumAdjacentPartNumbers();
	}

	private void fillAdjacentToSymbolMatrix() {
		adjacentToSymbol = new boolean[rows.size()][rowLength];

		for (int r = 0; r < rows.size(); ++r) {
			Matcher m = symbols.matcher(rows.get(r));
			while (m.find()) {
				if (m.start() != m.end() - 1) {
					throw new RuntimeException("start + " + m.start() + " != end-1 " + (m.end() - 1));
				}
				int c = m.start();
				if (c > 0) {
					if (r > 0) {
						adjacentToSymbol[r - 1][c - 1] = true;
					}
					adjacentToSymbol[r][c - 1] = true;
					if (r < rows.size() - 1) {
						adjacentToSymbol[r + 1][c - 1] = true;
					}
				}
				if (r > 0) {
					adjacentToSymbol[r - 1][c] = true;
				}
				if (r < rows.size() - 1) {
					adjacentToSymbol[r + 1][c] = true;
				}
				if (c < rowLength - 1) {
					if (r > 0) {
						adjacentToSymbol[r - 1][c + 1] = true;
					}
					adjacentToSymbol[r][c + 1] = true;
					if (r < rows.size() - 1) {
						adjacentToSymbol[r + 1][c + 1] = true;
					}
				}
			}
		}
	}

	private void fillNumAdjacentPartNumbers() {
		adjacentPartNumbers = new ArrayList<>();
		for (int r = 0; r < rows.size(); ++r) {
			ArrayList<List<Integer>> matrixRow = new ArrayList<>();
			for (int i = 0; i < rowLength; ++i) {
				matrixRow.add(new ArrayList<>());
			}
			adjacentPartNumbers.add(matrixRow);
		}

		for (int r = 0; r < rows.size(); ++r) {
			Matcher m = numbers.matcher(rows.get(r));
			while (m.find()) {
				int partNumber = Integer.parseInt(m.group());
				int c0 = m.start();
				int c1 = m.end();
				if (c0 > 0) {
					if (r > 0) {
						adjacentPartNumbers.get(r - 1).get(c0 - 1).add(partNumber);
					}
					adjacentPartNumbers.get(r).get(c0 - 1).add(partNumber);
					if (r < rows.size() - 1) {
						adjacentPartNumbers.get(r + 1).get(c0 - 1).add(partNumber);
					}
				}
				for (int c = c0; c < c1; ++c) {
					if (r > 0) {
						adjacentPartNumbers.get(r - 1).get(c).add(partNumber);
					}
					if (r < rows.size() - 1) {
						adjacentPartNumbers.get(r + 1).get(c).add(partNumber);
					}
				}
				if (c1 < rowLength) {
					if (r > 0) {
						adjacentPartNumbers.get(r - 1).get(c1).add(partNumber);
					}
					adjacentPartNumbers.get(r).get(c1).add(partNumber);
					if (r < rows.size() - 1) {
						adjacentPartNumbers.get(r + 1).get(c1).add(partNumber);
					}
				}
			}
		}
	}

	public void printAdjacentToSymbol() {
		if (!isFinal) {
			return;
		}

		for (int r = 0; r < adjacentToSymbol.length; ++r) {
			for (int c = 0; c < adjacentToSymbol[0].length; ++c) {
				if (adjacentToSymbol[r][c]) {
					System.out.print("+");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}

	public void printNumAdjacentPartNumbers() {
		if (!isFinal) {
			return;
		}

		for (int r = 0; r < adjacentPartNumbers.size(); ++r) {
			for (int c = 0; c < adjacentPartNumbers.get(0).size(); ++c) {
				System.out.print(adjacentPartNumbers.get(r).get(c).size());
			}
			System.out.println();
		}
	}

	public int sumOfPartNumbers() {
		int sumOfPartNumbers = 0;
		int r = 0;
		for (String row : rows) {
			Matcher m = numbers.matcher(row);

			while (m.find()) {
				for (int c = m.start(); c < m.end(); ++c) {
					if (adjacentToSymbol[r][c]) {
						int partNumber = Integer.parseInt(m.group());
						sumOfPartNumbers += partNumber;
						break;
					}
				}
			}
			r++;
		}
		return sumOfPartNumbers;
	}

	public int sumOfGearRatios() {
		int sumOfGearRatios = 0;
		int r = 0;
		for (String row : rows) {
			Matcher m = potentialGear.matcher(row);

			while (m.find()) {
				if (m.start() != m.end() - 1) {
					throw new RuntimeException("start + " + m.start() + " != end-1 " + (m.end() - 1));
				}
				int c = m.start();
				if (adjacentPartNumbers.get(r).get(c).size() == 2) {
					int gearRatio = adjacentPartNumbers.get(r).get(c).get(0) * adjacentPartNumbers.get(r).get(c).get(1);
					sumOfGearRatios += gearRatio;
				}
			}
			r++;
		}
		return sumOfGearRatios;
	}
}
