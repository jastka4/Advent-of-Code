package org.jastka4.adventofcode2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * --- Day 1: Report Repair ---
 * <p>
 * After saving Christmas five years in a row, you've decided to take a vacation at a nice resort on a tropical island.
 * Surely, Christmas will go on without you.
 * <p>
 * The tropical island has its own currency and is entirely cash-only. The gold coins used there have
 * a little picture of a starfish; the locals just call them stars. None of the currency exchanges
 * seem to have heard of them, but somehow, you'll need to find fifty of these coins by the time you arrive
 * so you can pay the deposit on your room.
 * <p>
 * To save your vacation, you need to get all fifty stars by December 25th.
 * <p>
 * Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar;
 * the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!
 * <p>
 * Before you leave, the Elves in accounting just need you to fix your expense report (your puzzle input);
 * apparently, something isn't quite adding up.
 * <p>
 * Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.
 * <p>
 * For example, suppose your expense report contained the following:
 * <p>
 * 1721
 * 979
 * 366
 * 299
 * 675
 * 1456
 * <p>
 * In this list, the two entries that sum to 2020 are 1721 and 299.
 * Multiplying them together produces 1721 * 299 = 514579, so the correct answer is 514579.
 * <p>
 * Of course, your expense report is much larger. Find the two entries that sum to 2020;
 * what do you get if you multiply them together?
 * <p>
 * --- Part Two ---
 * <p>
 * The Elves in accounting are thankful for your help;one of them even offers you a starfish coin
 * they had left over from a past vacation. They offer you a second one if you can find three numbers
 * in your expense report that meet the same criteria.
 * <p>
 * Using the above example again, the three entries that sum to 2020 are 979, 366, and 675.
 * Multiplying them together produces the answer, 241861950.
 * <p>
 * In your expense report, what is the product of the three entries that sum to 2020?
 */
public class Day1 {
    private static final String INPUT_CONFIG = "input.day1";
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
