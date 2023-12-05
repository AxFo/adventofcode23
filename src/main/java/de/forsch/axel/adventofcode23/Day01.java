package de.forsch.axel.adventofcode23;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class Day01 {

	public static int part = 1;

	private static Pattern firstDigit = Pattern.compile("^[^\\d]*(\\d)");
	private static Pattern lastDigit = Pattern.compile("(\\d)(?!.*\\d)");
	private static Pattern FW = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine|\\d)");
	private static Pattern BW = Pattern.compile("(eno|owt|eerht|ruof|evif|xis|neves|thgie|enin|\\d)");

	public static void main(String[] args) throws FileNotFoundException, IOException {
		int sumOfCalibrationValues = 0;

		try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/day01.input"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);

				sumOfCalibrationValues += Day01.decode(line);
				System.out.println(Day01.decode(line));
				System.out.println();
			}
		}

		System.out.println(sumOfCalibrationValues);
	}

	public static int decode(String line) {
		if (part == 1) {
			return decode1(line);
		} else {
			return decode2(line);
		}
	}

	public static int decode1(String line) {
		Matcher m1 = firstDigit.matcher(line);
		Matcher m2 = lastDigit.matcher(line);

		m1.find();
		m2.find();

		String s1 = line.substring(m1.end() - 1, m1.end());
		String s2 = m2.group();

		return Integer.parseInt(s1 + s2);
	}

	public static int decode2(String line) {
		Matcher fwm = FW.matcher(line);
		Matcher bwm = BW.matcher(new StringBuilder(line).reverse().toString());

		fwm.find();
		bwm.find();

		String d10 = convertToDigit(fwm.group());
		String d1 = convertToDigit(new StringBuilder(bwm.group()).reverse().toString());

		return Integer.parseInt(d10 + d1);
	}

	public static String convertToDigit(String group) {
		String digit;
		switch (group) {
		case "one":
			digit = "1";
			break;
		case "two":
			digit = "2";
			break;
		case "three":
			digit = "3";
			break;
		case "four":
			digit = "4";
			break;
		case "five":
			digit = "5";
			break;
		case "six":
			digit = "6";
			break;
		case "seven":
			digit = "7";
			break;
		case "eight":
			digit = "8";
			break;
		case "nine":
			digit = "9";
			break;
		default:
			digit = group;
		}
		return digit;
	}
}
