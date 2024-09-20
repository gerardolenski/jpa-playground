package org.gol.jpaplayground.infrastructure.provider;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.apache.commons.lang3.RandomUtils.nextInt;

@Component
public class AuthorProvider {

    private static final Path DATA_PATH = Path.of("src/main/resources/authors.txt");

    private final List<String> authors;

    public String drawAuthor() {
        return authors.get(nextInt(0, authors.size()));
    }

    AuthorProvider() {
        try (var in = Files.lines(DATA_PATH)) {
            this.authors = in.distinct().sorted().toList();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot init authors in memory DB", e);
        }
    }
}
