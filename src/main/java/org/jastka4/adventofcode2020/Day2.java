package org.jastka4.adventofcode2020;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * --- Day 2: Password Philosophy ---
 * <p>
 * Your flight departs in a few days from the coastal airport;
 * the easiest way down to the coast from here is via toboggan.
 * <p>
 * The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day.
 * "Something's wrong with our computers; we can't log in!" You ask if you can take a look.
 * <p>
 * Their password database seems to be a little corrupted: some of the passwords wouldn't have been allowed
 * by the Official Toboggan Corporate Policy that was in effect when they were chosen.
 * <p>
 * To try to debug the problem, they have created a list (your puzzle input) of passwords
 * (according to the corrupted database) and the corporate policy when that password was set.
 * <p>
 * For example, suppose you have the following list:
 * <p>
 * 1-3 a: abcde
 * 1-3 b: cdefg
 * 2-9 c: ccccccccc
 * <p>
 * Each line gives the password policy and then the password. The password policy indicates the lowest
 * and highest number of times a given letter must appear for the password to be valid.
 * For example, 1-3 a means that the password must contain a at least 1 time and at most 3 times.
 * <p>
 * In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b,
 * but needs at least 1. The first and third passwords are valid: they contain one a or nine c,
 * both within the limits of their respective policies.
 * <p>
 * How many passwords are valid according to their policies?
 * <p>
 * --- Part Two ---
 * <p>
 * While it appears you validated the passwords correctly, they don't seem to be what
 * the Official Toboggan Corporate Authentication System is expecting.
 * <p>
 * The shopkeeper suddenly realizes that he just accidentally explained the password policy rules from his old job
 * at the sled rental place down the street! The Official Toboggan Corporate Policy actually works a little differently.
 * <p>
 * Each policy actually describes two positions in the password, where 1 means the first character,
 * 2 means the second character, and so on. (Be careful; Toboggan Corporate Policies have no concept of "index zero"!)
 * Exactly one of these positions must contain the given letter. Other occurrences of the letter are irrelevant
 * for the purposes of policy enforcement.
 * <p>
 * Given the same example list from above:
 * <p>
 * 1-3 a: abcde is valid: position 1 contains a and position 3 does not.
 * 1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
 * 2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
 * <p>
 * How many passwords are valid according to the new interpretation of the policies?
 */
public class Day2 {

    private static final String INPUT_CONFIG = "input.day2";

    @SuppressWarnings("java:S106")
    public static void main(String... args) {
        final Day2 day2 = new Day2();
        final Properties prop = Utils.getProperties();
        final List<String> allLines = Utils.getLinesFromFile(prop.getProperty(INPUT_CONFIG));
        try {
            System.out.println(day2.solve(allLines));
            System.out.println(day2.solve2(allLines));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Time complexity: O(?) \\ TODO - check
     * Space complexity: O(?)
     */
    private int solve(final List<String> allLines) throws Exception {
        int validPasswords = 0;

        for (String line : allLines) {
            final Triple<Pair<Integer, Integer>, Character, String> entry = splitLine(line);
            final int occurrences = countCharOccurrences(entry);
            final Pair<Integer, Integer> range = getRange(entry);

            if (occurrences >= range.getLeft() && occurrences <= range.getRight()) {
                validPasswords++;
            }
        }

        return validPasswords;
    }

    /**
     * Time complexity: O(?) \\ TODO - check
     * Space complexity: O(?)
     */
    private int solve2(final List<String> allLines) throws Exception {
        int validPasswords = 0;

        for (String line : allLines) {
            final Triple<Pair<Integer, Integer>, Character, String> entry = splitLine(line);
            final int occurrences = countCharOccurrencesAtPositions(entry);

            if (occurrences == 1) {
                validPasswords++;
            }
        }

        return validPasswords;
    }

    private Triple<Pair<Integer, Integer>, Character, String> splitLine(final String line) throws Exception {
        final Pattern pattern = Pattern.compile("(\\d*)-(\\d*) (\\w): (\\w*)");
        final Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            final Pair<Integer, Integer> range = findRange(matcher);
            final Character character = matcher.group(3).charAt(0);
            final String password = matcher.group(4);

            return new ImmutableTriple<>(range, character, password);
        } else {
            throw new Exception("Line does not match the pattern!");
        }
    }

    private Pair<Integer, Integer> findRange(final Matcher matcher) {
        final Integer r1 = Integer.parseInt(matcher.group(1));
        final Integer r2 = Integer.parseInt(matcher.group(2));
        return new ImmutablePair<>(r1, r2);
    }

    private int countCharOccurrences(final Triple<Pair<Integer, Integer>, Character, String> entry) {
        final char character = getCharacter(entry);
        final String password = getPassword(entry);

        return StringUtils.countMatches(password, character);
    }

    private int countCharOccurrencesAtPositions(final Triple<Pair<Integer, Integer>, Character, String> entry) {
        int counter = 0;

        final char character = getCharacter(entry);
        final Pair<Integer, Integer> positions = getRange(entry);
        final String password = getPassword(entry);

        if (password.charAt(positions.getLeft() - 1) == character) {
            counter++;
        }
        if (password.charAt(positions.getRight() - 1) == character) {
            counter++;
        }

        return counter;
    }

    private Pair<Integer, Integer> getRange(final Triple<Pair<Integer, Integer>, Character, String> entry) {
        return entry.getLeft();
    }

    private char getCharacter(final Triple<Pair<Integer, Integer>, Character, String> entry) {
        return entry.getMiddle();
    }

    private String getPassword(final Triple<Pair<Integer, Integer>, Character, String> entry) {
        return entry.getRight();
    }
}
