package org.jastka4.adventofcode.edition2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * <h2>--- Day 1: Report Repair ---</h2>
 * <p>After saving Christmas <a href="https://adventofcode.com/events">five years in a row</a>,
 * you've decided to take a vacation at a nice resort on a tropical island.
 * <span title="WHAT COULD GO WRONG">Surely</span>, Christmas will go on without you.</p>
 * <p>The tropical island has its own currency and is entirely cash-only.
 * The gold coins used there have a little picture of a starfish;
 * the locals just call them <em class="star">stars</em>.
 * None of the currency exchanges seem to have heard of them, but somehow,
 * you'll need to find fifty of these coins by the time you arrive so you can pay the deposit on your room.</p>
 * <p>To save your vacation, you need to get all <em class="star">fifty stars</em> by December 25th.</p>
 * <p>Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar;
 * the second puzzle is unlocked when you complete the first.
 * Each puzzle grants <em class="star">one star</em>. Good luck!</p>
 * <p>Before you leave, the Elves in accounting just need you to fix your <em>expense report</em> (your puzzle input);
 * apparently, something isn't quite adding up.</p>
 * <p>Specifically, they need you to <em>find the two entries that sum to <code>2020</code></em> and
 * then multiply those two numbers together.</p>
 * <p>For example, suppose your expense report contained the following:</p>
 * <pre><code>1721
 * 979
 * 366
 * 299
 * 675
 * 1456
 * </code></pre>
 * <p>In this list, the two entries that sum to <code>2020</code> are <code>1721</code> and <code>299</code>.
 * Multiplying them together produces <code>1721 * 299 = 514579</code>,
 * so the correct answer is <code><em>514579</em></code>.</p>
 * <p>Of course, your expense report is much larger. <em>Find the two entries that sum to <code>2020</code>;
 * what do you get if you multiply them together?</em></p>
 * <br>
 * <h2>--- Part Two ---</h2>
 * <p>The Elves in accounting are thankful for your help; one of them even offers you a starfish coin
 * they had left over from a past vacation. They offer you a second one if you can find <em>three</em> numbers
 * in your expense report that meet the same criteria.</p>
 * <p>Using the above example again, the three entries that sum to <code>2020</code> are <code>979</code>,
 * <code>366</code>, and <code>675</code>. Multiplying them together produces the answer,
 * <code><em>241861950</em></code>.</p>
 * <p>In your expense report, <em>what is the product of the three entries that sum to <code>2020</code>?</em></p>
 */
public class Day1 {
    private static final String INPUT_CONFIG = "input.2020.day1";
    private static final int SUM = 2020;

    @SuppressWarnings("java:S106")
    public static void main(final String... args) {
        final Day1 day1 = new Day1();
        final Properties prop = Utils.getProperties();
        final List<Integer> integers = Utils.getIntegersFromFile(prop.getProperty(INPUT_CONFIG));

        day1.solve1(integers, SUM).forEach(System.out::println);
        day1.solve2(integers, SUM).forEach(System.out::println);
    }

    /**
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    private List<Integer> solve1(final List<Integer> integers, final int sum) {
        return getMultiplicationsOfIntegersThatSumUpToValue(integers, sum);
    }

    /**
     * Time complexity: O(n^2)
     * Space complexity: O(n)
     */
    private List<Integer> solve2(final List<Integer> integers, final int sum) {
        final List<Integer> multiplications = new ArrayList<>();
        for (int i = 0; i < integers.size() - 2; i++) {
            final int temp = sum - integers.get(i);
            final List<Integer> remainingIntegers = integers.subList(i + 1, integers.size() - 1);
            for (Integer mul : getMultiplicationsOfIntegersThatSumUpToValue(remainingIntegers, temp)) {
                multiplications.add(mul * integers.get(i));
            }
        }

        return multiplications;
    }

    private List<Integer> getMultiplicationsOfIntegersThatSumUpToValue(final List<Integer> integers, final int value) {
        final Set<Integer> sums = new HashSet<>();
        final List<Integer> multiplications = new ArrayList<>();

        for (Integer integer : integers) {
            final Integer temp = value - integer;
            if (sums.contains(temp)) {
                multiplications.add(integer * (value - integer));
            }
            sums.add(integer);
        }

        return multiplications;
    }
}
