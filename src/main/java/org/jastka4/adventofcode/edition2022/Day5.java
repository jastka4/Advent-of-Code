package org.jastka4.adventofcode.edition2022;

import org.apache.commons.lang3.StringUtils;
import org.jastka4.adventofcode.edition2020.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h2>--- Day 5: Supply Stacks ---</h2>
 * <p>The expedition can depart as soon as the final supplies have been unloaded from the ships.
 * Supplies are stored in stacks of marked <em>crates</em>, but because the needed supplies are buried under
 * many other crates, the crates need to be rearranged.</p>
 * <p>The ship has a <em>giant cargo crane</em> capable of moving crates between stacks.
 * To ensure none of the crates get crushed or fall over, the crane operator will rearrange them
 * in a series of carefully-planned steps. After the crates are rearranged,
 * the desired crates will be at the top of each stack.</p>
 * <p>The Elves don't want to interrupt the crane operator during this delicate procedure,
 * but they forgot to ask her <em>which</em> crate will end up where, and they want to be ready to unload them
 * as soon as possible so they can embark.</p>
 * <p>They do, however, have a drawing of the starting stacks of crates <em>and</em>
 * the rearrangement procedure (your puzzle input). For example:</p>
 * <pre><code>
 *     [D]
 * [N] [C]
 * [Z] [M] [P]
 *  1   2   3
 *
 * move 1 from 2 to 1
 * move 3 from 1 to 3
 * move 2 from 2 to 1
 * move 1 from 1 to 2
 * </code></pre>
 * <p>In this example, there are three stacks of crates. Stack 1 contains two crates:
 * crate <code>Z</code> is on the bottom, and crate <code>N</code> is on top. Stack 2 contains three crates;
 * from bottom to top, they are crates <code>M</code>, <code>C</code>, and <code>D</code>.
 * Finally, stack 3 contains a single crate, <code>P</code>.</p>
 * <p>Then, the rearrangement procedure is given. In each step of the procedure,
 * a quantity of crates is moved from one stack to a different stack.
 * In the first step of the above rearrangement procedure, one crate is moved from stack 2 to stack 1,
 * resulting in this configuration:</p>
 * <pre><code>
 * [D]
 * [N] [C]
 * [Z] [M] [P]
 *  1   2   3
 * </code></pre>
 * <p>In the second step, three crates are moved from stack 1 to stack 3.
 * Crates are moved <em>one at a time</em>, so the first crate to be moved (<code>D</code>) ends up
 * below the second and third crates:</p>
 * <pre><code>
 *         [Z]
 *         [N]
 *     [C] [D]
 *     [M] [P]
 *  1   2   3
 * </code></pre>
 * <p>Then, both crates are moved from stack 2 to stack 1.
 * Again, because crates are moved <em>one at a time</em>,
 * crate <code>C</code> ends up below crate <code>M</code>:</p>
 * <pre><code>
 *         [Z]
 *         [N]
 * [M]     [D]
 * [C]     [P]
 *  1   2   3
 * </code></pre>
 * <p>Finally, one crate is moved from stack 1 to stack 2:</p>
 * <pre><code>
 *         [<em>Z</em>]
 *         [N]
 *         [D]
 * [<em>C</em>] [<em>M</em>] [P]
 *  1   2   3
 * </code></pre>
 * <p>The Elves just need to know <em>which crate will end up on top of each stack</em>;
 * in this example, the top crates are <code>C</code> in stack 1,
 * <code>M</code> in stack 2, and <code>Z</code> in stack 3,
 * so you should combine these together and give the Elves the message <code><em>CMZ</em></code>.</p>
 * <p><em>After the rearrangement procedure completes, what crate ends up on top of each stack?</em></p>
 * <br>
 * <h2>--- Part Two ---</h2>
 * <p>As you watch the crane operator expertly rearrange the crates,
 * you notice the process isn't following your prediction.</p>
 * <p>Some mud was covering the writing on the side of the crane, and you quickly wipe it away.
 * The crane isn't a CrateMover 9000 - it's a
 * <em><span title="It's way better than the old CrateMover 1006.">CrateMover 9001</span></em>.</p>
 * <p>The CrateMover 9001 is notable for many new and exciting features: air conditioning,
 * leather seats, an extra cup holder, and <em>the ability to pick up and move multiple crates at once</em>.</p>
 * <p>Again considering the example above, the crates begin in the same configuration:</p>
 * <pre><code>
 *     [D]
 * [N] [C]
 * [Z] [M] [P]
 *  1   2   3
 * </code></pre>
 * <p>Moving a single crate from stack 2 to stack 1 behaves the same as before:</p>
 * <pre><code>
 * [D]
 * [N] [C]
 * [Z] [M] [P]
 *  1   2   3
 * </code></pre>
 * <p>However, the action of moving three crates from stack 1 to stack 3 means that those three
 * moved crates <em>stay in the same order</em>, resulting in this new configuration:</p>
 * <pre><code>
 *         [D]
 *         [N]
 *     [C] [Z]
 *     [M] [P]
 *  1   2   3
 * </code></pre>
 * <p>Next, as both crates are moved from stack 2 to stack 1,
 * they <em>retain their order</em> as well:</p>
 * <pre><code>
 *         [D]
 *         [N]
 * [C]     [Z]
 * [M]     [P]
 *  1   2   3
 * </code></pre>
 * <p>Finally, a single crate is still moved from stack 1 to stack 2,
 * but now it's crate <code>C</code> that gets moved:</p>
 * <pre><code>
 *         [<em>D</em>]
 *         [N]
 *         [Z]
 * [<em>M</em>] [<em>C</em>] [P]
 *  1   2   3
 * </code></pre>
 * <p>In this example, the CrateMover 9001 has put the crates in a totally different order:
 * <code><em>MCD</em></code>.</p>
 * <p>Before the rearrangement process finishes, update your simulation so that the Elves know
 * where they should stand to be ready to unload the final supplies. <em>After the rearrangement procedure completes,
 * what crate ends up on top of each stack?</em></p>
 */
public class Day5 {
    private static final String INPUT_CONFIG = "input.2022.day5";

    public static void main(final String... args) {
        final Day5 day5 = new Day5();
        final Properties prop = Utils.getProperties();
        final List<String> allLines = Utils.getLinesFromFile(prop.getProperty(INPUT_CONFIG));

        final List<Move> moves = getMoves(allLines);

        try {
            System.out.println("Part 1: " + day5.solve(getCrates(allLines), moves));
            System.out.println("Part 2: " + day5.solve2(getCrates(allLines), moves));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Stack<Character>> getCrates(final List<String> lines) {
        List<Stack<Character>> crates = new ArrayList<>();

        for (String line : lines) {
            if (line.startsWith(" 1")) {
                String[] columns = line.strip().split(" {3}");
                for (int j = 0; j < columns.length; j++) {
                    Stack<Character> column = new Stack<>();
                    crates.add(column);
                }
                break;
            }
        }

        for (int i = lines.size(); i-- > 0; ) {
            if (!lines.get(i).startsWith("move") && !lines.get(i).isBlank()) {
                final Pattern pattern = Pattern.compile("(\\[\\w\\]| {3}) ?");
                final Matcher matcher = pattern.matcher(lines.get(i));
                final List<String> columns = new ArrayList<>();
                while (matcher.find()) {
                    columns.add(matcher.group());
                }
                for (int j = 0; j < columns.size(); j++) {
                    if (!columns.get(j).isBlank()) {
                        crates.get(j).push(columns.get(j).charAt(1));
                    }
                }
            }
        }

        return crates;
    }

    private static List<Move> getMoves(List<String> lines) {
        List<Move> moves = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("move")) {
                String[] parts = line.split(" ");
                Move move = new Move(parts[1], parts[3], parts[5]);
                moves.add(move);
            }
        }
        return moves;
    }

    private String solve(final List<Stack<Character>> crates, final List<Move> moves) {
        for (Move move : moves) {
            for (int i = 0; i < move.quantity; i++) {
                Character crate = crates.get(move.from - 1).pop();
                crates.get(move.to - 1).push(crate);
            }
        }

        StringBuilder result = new StringBuilder(StringUtils.EMPTY);
        for (Stack<Character> column : crates) {
            result.append(column.peek());
        }

        return result.toString();
    }

    private String solve2(final List<Stack<Character>> crates, final List<Move> moves) {
        for (Move move : moves) {
            List<Character> temp = new Stack<>();
            for (int i = 0; i < move.quantity; i++) {
                Character crate = crates.get(move.from - 1).pop();
                temp.add(crate);
            }

            for (int i = temp.size(); i-- > 0; ) {
                crates.get(move.to - 1).push(temp.get(i));
            }
        }

        StringBuilder result = new StringBuilder(StringUtils.EMPTY);
        for (Stack<Character> column : crates) {
            result.append(column.peek());
        }

        return result.toString();
    }

    static class Move {
        int quantity;
        int from;
        int to;

        public Move(String quantity, String from, String to) {
            this.quantity = Integer.parseInt(quantity);
            this.from = Integer.parseInt(from);
            this.to = Integer.parseInt(to);
        }
    }
}
