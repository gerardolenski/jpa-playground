package org.gol.jpaplayground.infrastructure.provider;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.apache.commons.lang3.RandomUtils.nextInt;

@Component
public class SentenceProvider {

    private static final Path DATA_PATH = Path.of("src/main/resources/words.txt");

    private final List<String> words;

    public String drawSentence(int maxSize) {
        var builder = new StringBuilder();
        builder.append(drawWord());
        while (true) {
            var w = drawWord();
            if (builder.length() + w.length() + 1 <= maxSize) {
                builder
                        .append(" ")
                        .append(w);
            } else {
                return builder.toString();
            }
        }
    }

    private String drawWord() {
        return words.get(nextInt(0, words.size()));
    }

    SentenceProvider() {
        try (var in = Files.lines(DATA_PATH)) {
            this.words = in.distinct().sorted().toList();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot init words in memory DB", e);
        }
    }
}
