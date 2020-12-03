package org.jastka4.adventofcode2020;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class Utils {

    private Utils() {
        // empty
    }

    private static final String CONFIG_PROPERTIES = "config.properties";

    public static List<String> getLinesFromFile(final String relativePath) {
        final List<String> allLines = new ArrayList<>();
        try {
            final URL resource = Utils.class.getResource(relativePath);
            allLines.addAll(Files.readAllLines(Paths.get(resource.toURI())));
        } catch (final IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return allLines;
    }

    public static List<Integer> getIntegersFromFile(final String relativePath) {
        return convertToIntegers(getLinesFromFile(relativePath));
    }

    public static Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream input = Day1.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES)) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            }

            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

    private static List<Integer> convertToIntegers(final List<String> strings) {
        return strings.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
