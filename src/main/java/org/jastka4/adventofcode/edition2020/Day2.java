package org.jastka4.adventofcode.edition2020;

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
 * <h2>--- Day 2: Password Philosophy ---</h2>
 * <p>Your flight departs in a few days from the coastal airport; the easiest way down to the coast
 * from here is via <a href="https://en.wikipedia.org/wiki/Toboggan" target="_blank">toboggan</a>.</p>
 * <p>The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day.
 * "Something's wrong with our computers; we can't log in!" You ask if you can take a look.</p>
 * <p>Their password database seems to be a little corrupted:
 * some of the passwords wouldn't have been allowed by the <span title="To ensure your safety,
 * your password must be the following string...">Official Toboggan Corporate Policy</span>
 * that was in effect when they were chosen.</p>
 * <p>To try to debug the problem, they have created a list (your puzzle input) of <em>passwords</em>
 * (according to the corrupted database) and <em>the corporate policy when that password was set</em>.</p>
 * <p>For example, suppose you have the following list:</p>
 * <pre><code>
 * 1-3 a: abcde
 * 1-3 b: cdefg
 * 2-9 c: ccccccccc
 * </code></pre>
 * <p>Each line gives the password policy and then the password.
 * The password policy indicates the lowest and highest number of times a given letter must appear
 * for the password to be valid. For example, <code>1-3 a</code> means that the password
 * must contain <code>a</code> at least <code>1</code> time and at most <code>3</code> times.</p>
 * <p>In the above example, <code><em>2</em></code> passwords are valid.
 * The middle password, <code>cdefg</code>, is not; it contains no instances of <code>b</code>,
 * but needs at least <code>1</code>. The first and third passwords are valid:
 * they contain one <code>a</code> or nine <code>c</code>, both within the limits of their respective policies.</p>
 * <p><em>How many passwords are valid</em> according to their policies?</p>
 * <br>
 * <h2>--- Part Two ---</h2>
 * <p>While it appears you validated the passwords correctly,
 * they don't seem to be what the Official Toboggan Corporate Authentication System is expecting.</p>
 * <p>The shopkeeper suddenly realizes that he just accidentally explained the password policy rules
 * from his old job at the sled rental place down the street!
 * The Official Toboggan Corporate Policy actually works a little differently.</p>
 * <p>Each policy actually describes two <em>positions in the password</em>,
 * where <code>1</code> means the first character, <code>2</code> means the second character, and so on.
 * (Be careful; Toboggan Corporate Policies have no concept of "index zero"!)
 * <em>Exactly one of these positions</em> must contain the given letter.
 * Other occurrences of the letter are irrelevant for the purposes of policy enforcement.</p>
 * <p>Given the same example list from above:</p>
 * <ul>
 * <li><code>1-3 a: <em>a</em>b<em>c</em>de</code> is <em>valid</em>:
 * position <code>1</code> contains <code>a</code> and position <code>3</code> does not.</li>
 * <li><code>1-3 b: <em>c</em>d<em>e</em>fg</code> is <em>invalid</em>:
 * neither position <code>1</code> nor position <code>3</code> contains <code>b</code>.</li>
 * <li><code>2-9 c: c<em>c</em>cccccc<em>c</em></code> is <em>invalid</em>:
 * both position <code>2</code> and position <code>9</code> contain <code>c</code>.</li>
 * </ul>
 * <p><em>How many passwords are valid</em> according to the new interpretation of the policies?</p>
 */
public class Day2 {

    private static final String INPUT_CONFIG = "input.2020.day2";

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
