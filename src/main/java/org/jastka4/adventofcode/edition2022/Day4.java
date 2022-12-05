package org.jastka4.adventofcode.edition2022;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.tuple.Pair;
import org.jastka4.adventofcode.edition2020.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * <h2>--- Day 4: Camp Cleanup ---</h2>
 * <p>Space needs to be cleared before the last supplies can be unloaded from the ships,
 * and so several Elves have been assigned the job of cleaning up sections of the camp.
 * Every section has a unique <em>ID number</em>, and each Elf is assigned a range of section IDs.</p>
 * <p>However, as some of the Elves compare their section assignments with each other,
 * they've noticed that many of the assignments <em>overlap</em>.
 * To try to quickly find overlaps and reduce duplicated effort,
 * the Elves pair up and make a <em>big list of the section assignments for each pair</em> (your puzzle input).</p>
 * <p>For example, consider the following list of section assignment pairs:</p>
 * <pre><code>
 * 2-4,6-8
 * 2-3,4-5
 * 5-7,7-9
 * 2-8,3-7
 * 6-6,4-6
 * 2-6,4-8
 * </code></pre>
 * <p>For the first few pairs, this list means:</p>
 * <ul>
 * <li>Within the first pair of Elves, the first Elf was assigned sections <code>2-4</code>
 * (sections <code>2</code>, <code>3</code>, and <code>4</code>), while the second Elf was assigned
 * sections <code>6-8</code> (sections <code>6</code>, <code>7</code>, <code>8</code>).</li>
 * <li>The Elves in the second pair were each assigned two sections.</li>
 * <li>The Elves in the third pair were each assigned three sections:
 * one got sections <code>5</code>, <code>6</code>, and <code>7</code>,
 * while the other also got <code>7</code>, plus <code>8</code> and <code>9</code>.</li>
 * </ul>
 * <p>This example list uses single-digit section IDs to make it easier to draw;
 * your actual list might contain larger numbers. Visually, these pairs of section assignments look like this:</p>
 * <pre><code>
 * .234.....  2-4
 * .....678.  6-8
 *
 * .23......  2-3
 * ...45....  4-5
 *
 * ....567..  5-7
 * ......789  7-9
 *
 * .2345678.  2-8
 * ..34567..  3-7
 *
 * .....6...  6-6
 * ...456...  4-6
 *
 * .23456...  2-6
 * ...45678.  4-8
 * </code></pre>
 * <p>Some of the pairs have noticed that one of their assignments <em>fully contains</em> the other.
 * For example, <code>2-8</code> fully contains <code>3-7</code>, and <code>6-6</code>
 * is fully contained by <code>4-6</code>. In pairs where one assignment fully contains the other,
 * one Elf in the pair would be exclusively cleaning sections their partner will already be cleaning,
 * so these seem like the most in need of reconsideration.
 * In this example, there are <code><em>2</em></code> such pairs.</p>
 * <p><em>In how many assignment pairs does one range fully contain the other?</em></p>
 * <br>
 * <h2>--- Part Two ---</h2>
 * <p>It seems like there is still quite a bit of duplicate work planned.
 * Instead, the Elves would <span title="If you like this,
 * you'll *love* axis-aligned bounding box intersection testing.">like</span> to know the number of pairs
 * that <em>overlap at all</em>.</p>
 * <p>In the above example, the first two pairs (<code>2-4,6-8</code> and <code>2-3,4-5</code>) don't overlap,
 * while the remaining four pairs (<code>5-7,7-9</code>, <code>2-8,3-7</code>,
 * <code>6-6,4-6</code>, and <code>2-6,4-8</code>) do overlap:</p>
 * <ul>
 * <li><code>5-7,7-9</code> overlaps in a single section, <code>7</code>.</li>
 * <li><code>2-8,3-7</code> overlaps all of the sections <code>3</code> through <code>7</code>.</li>
 * <li><code>6-6,4-6</code> overlaps in a single section, <code>6</code>.</li>
 * <li><code>2-6,4-8</code> overlaps in sections <code>4</code>, <code>5</code>, and <code>6</code>.</li>
 * </ul>
 * <p>So, in this example, the number of overlapping assignment pairs is <code><em>4</em></code>.</p>
 * <p><em>In how many assignment pairs do the ranges overlap?</em></p>
 */
public class Day4 {
    private static final String INPUT_CONFIG = "input.2022.day4";

    public static void main(String... args) {
        final Day4 day4 = new Day4();
        final Properties prop = Utils.getProperties();
        final List<String> allLines = Utils.getLinesFromFile(prop.getProperty(INPUT_CONFIG));

        final List<Pair<Range<Integer>, Range<Integer>>> pairs = createPairs(allLines);

        try {
            System.out.println("Part 1: " + day4.solve(pairs));
            System.out.println("Part 2: " + day4.solve2(pairs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int solve(List<Pair<Range<Integer>, Range<Integer>>> pairs) {
        int result = 0;

        for (Pair<Range<Integer>, Range<Integer>> pair : pairs) {
            if (pair.getLeft().containsRange(pair.getRight()) || pair.getRight().containsRange(pair.getLeft())) {
                result++;
            }
        }

        return result;
    }

    private int solve2(List<Pair<Range<Integer>, Range<Integer>>> pairs) {
        int result = 0;
        for (Pair<Range<Integer>, Range<Integer>> pair : pairs) {

            if (pair.getLeft().isOverlappedBy(pair.getRight())) {
                result++;
            }
        }
        return result;
    }

    private static List<Pair<Range<Integer>, Range<Integer>>> createPairs(List<String> allLines) {
        final List<Pair<Range<Integer>, Range<Integer>>> pairs = new ArrayList<>();
        for (String line : allLines) {
            final String[] parts = line.split(",");
            final String[] firstParts = parts[0].split("-");
            final String[] secondParts = parts[1].split("-");
            final Range<Integer> firstRange = Range.between(Integer.valueOf(firstParts[0]), Integer.valueOf(firstParts[1]));
            final Range<Integer> secondRange = Range.between(Integer.valueOf(secondParts[0]), Integer.valueOf(secondParts[1]));

            pairs.add(Pair.of(firstRange, secondRange));
        }
        return pairs;
    }
}
