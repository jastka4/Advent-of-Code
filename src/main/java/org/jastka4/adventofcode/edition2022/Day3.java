package org.jastka4.adventofcode.edition2022;

import org.jastka4.adventofcode.edition2020.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * <h2>--- Day 3: Rucksack Reorganization ---</h2>
 * <p>One Elf has the important job of loading all of the
 * <a href="https://en.wikipedia.org/wiki/Rucksack" target="_blank">rucksacks</a> with supplies
 * for the <span title="Where there's jungle, there's hijinxs.">jungle</span> journey.
 * Unfortunately, that Elf didn't quite follow the packing instructions,
 * and so a few items now need to be rearranged.</p>
 * <p>Each rucksack has two large <em>compartments</em>. All items of a given type
 * are meant to go into exactly one of the two compartments. The Elf that did the packing
 * failed to follow this rule for exactly one item type per rucksack.</p>
 * <p>The Elves have made a list of all of the items currently in each rucksack (your puzzle input),
 * but they need your help finding the errors. Every item type is identified by a single lowercase
 * or uppercase letter (that is, <code>a</code> and <code>A</code> refer to different types of items).</p>
 * <p>The list of items for each rucksack is given as characters all on a single line.
 * A given rucksack always has the same number of items in each of its two compartments,
 * so the first half of the characters represent items in the first compartment,
 * while the second half of the characters represent items in the second compartment.</p>
 * <p>For example, suppose you have the following list of contents from six rucksacks:</p>
 * <pre><code>
 * vJrwpWtwJgWrhcsFMMfFFhFp
 * jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
 * PmmdzqPrVvPwwTWBwg
 * wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
 * ttgJtRGJQctTZtZT
 * CrZsJsPPZsGzwwsLwLmpwMDw
 * </code></pre>
 * <ul>
 * <li>The first rucksack contains the items <code>vJrwpWtwJgWrhcsFMMfFFhFp</code>,
 * which means its first compartment contains the items <code>vJrwpWtwJgWr</code>,
 * while the second compartment contains the items <code>hcsFMMfFFhFp</code>.
 * The only item type that appears in both compartments is lowercase <code><em>p</em></code>.</li>
 * <li>The second rucksack's compartments contain <code>jqHRNqRjqzjGDLGL</code>
 * and <code>rsFMfFZSrLrFZsSL</code>. The only item type that appears in both compartments
 * is uppercase <code><em>L</em></code>.</li>
 * <li>The third rucksack's compartments contain <code>PmmdzqPrV</code> and <code>vPwwTWBwg</code>;
 * the only common item type is uppercase <code><em>P</em></code>.</li>
 * <li>The fourth rucksack's compartments only share item type <code><em>v</em></code>.</li>
 * <li>The fifth rucksack's compartments only share item type <code><em>t</em></code>.</li>
 * <li>The sixth rucksack's compartments only share item type <code><em>s</em></code>.</li>
 * </ul>
 * <p>To help prioritize item rearrangement, every item type can be converted to a <em>priority</em>:</p>
 * <ul>
 * <li>Lowercase item types <code>a</code> through <code>z</code> have priorities 1 through 26.</li>
 * <li>Uppercase item types <code>A</code> through <code>Z</code> have priorities 27 through 52.</li>
 * </ul>
 * <p>In the above example, the priority of the item type that appears in both compartments
 * of each rucksack is 16 (<code>p</code>), 38 (<code>L</code>), 42 (<code>P</code>), 22 (<code>v</code>),
 * 20 (<code>t</code>), and 19 (<code>s</code>); the sum of these is <code><em>157</em></code>.</p>
 * <p>Find the item type that appears in both compartments of each rucksack.
 * <em>What is the sum of the priorities of those item types?</em></p>
 * <br>
 * <h2>--- Part Two ---</h2>
 * <p>As you finish identifying the misplaced items, the Elves come to you with another issue.</p>
 * <p>For safety, the Elves are divided into groups of three. Every Elf carries
 * a badge that identifies their group. For efficiency, within each group of three Elves,
 * the badge is the <em>only item type carried by all three Elves</em>.
 * That is, if a group's badge is item type <code>B</code>,
 * then all three Elves will have item type <code>B</code> somewhere in their rucksack,
 * and at most two of the Elves will be carrying any other item type.</p>
 * <p>The problem is that someone forgot to put this year's updated authenticity sticker on the badges.
 * All of the badges need to be pulled out of the rucksacks so the new authenticity stickers can be attached.</p>
 * <p>Additionally, nobody wrote down which item type corresponds to each group's badges.
 * The only way to tell which item type is the right one is by finding the one item type that
 * is <em>common between all three Elves</em> in each group.</p>
 * <p>Every set of three lines in your list corresponds to a single group, but each group can have
 * a different badge item type. So, in the above example, the first group's rucksacks are the first three lines:</p>
 * <pre><code>vJrwpWtwJgWrhcsFMMfFFhFp
 * jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
 * PmmdzqPrVvPwwTWBwg
 * </code></pre>
 * <p>And the second group's rucksacks are the next three lines:</p>
 * <pre><code>wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
 * ttgJtRGJQctTZtZT
 * CrZsJsPPZsGzwwsLwLmpwMDw
 * </code></pre>
 * <p>In the first group, the only item type that appears in all three rucksacks is lowercase <code>r</code>;
 * this must be their badges. In the second group, their badge item type must be <code>Z</code>.</p>
 * <p>Priorities for these items must still be found to organize the sticker attachment efforts: here,
 * they are 18 (<code>r</code>) for the first group and 52 (<code>Z</code>) for the second group.
 * The sum of these is <code><em>70</em></code>.</p>
 * <p>Find the item type that corresponds to the badges of each three-Elf group.
 * <em>What is the sum of the priorities of those item types?</em></p>
 */
public class Day3 {
    private static final String INPUT_CONFIG = "input.2022.day3";

    public static void main(String... args) {
        final Day3 day3 = new Day3();
        final Properties prop = Utils.getProperties();
        final List<String> allLines = Utils.getLinesFromFile(prop.getProperty(INPUT_CONFIG));
        try {
            System.out.println("Part 1: " + day3.solve(allLines));
            System.out.println("Part 2: " + day3.solve2(allLines));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int solve(List<String> lines) {
        int result = 0;
        for (String line : lines) {
            String firstHalf = line.substring(0, line.length() / 2);
            String secondHalf = line.substring(line.length() / 2);

            for (char c : firstHalf.toCharArray()) {
                boolean contains = secondHalf.contains(String.valueOf(c));
                if (contains) {
                    if (Character.isUpperCase(c)) {
                        result += c - 38;
                    } else {
                        result += c - 96;
                    }
                    break;
                }
            }
        }

        return result;
    }

    private int solve2(List<String> lines) {
        int result = 0;

        for (int i = 0; i < lines.size(); i += 3) {
            Set<Character> common = new HashSet<>();
            Set<Character> set2 = new HashSet<>();
            Set<Character> set3 = new HashSet<>();

            for (char c : lines.get(i).toCharArray()) {
                common.add(c);
            }
            for (char c : lines.get(i + 1).toCharArray()) {
                set2.add(c);
            }
            for (char c : lines.get(i + 2).toCharArray()) {
                set3.add(c);
            }

            common.retainAll(set2);
            common.retainAll(set3);

            for (Character c : common) {
                if (Character.isUpperCase(c)) {
                    result += c - 38;
                } else {
                    result += c - 96;
                }
                break;
            }
        }

        return result;
    }
}
