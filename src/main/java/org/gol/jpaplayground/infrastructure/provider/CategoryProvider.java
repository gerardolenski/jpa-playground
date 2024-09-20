package org.gol.jpaplayground.infrastructure.provider;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.apache.commons.lang3.RandomUtils.nextInt;

@Component
public class CategoryProvider {

    private static final Path DATA_PATH = Path.of("src/main/resources/categories.txt");

    private final List<String> categories;

    public String drawCategory() {
        return categories.get(nextInt(0, categories.size()));
    }

    CategoryProvider() {
        try (var in = Files.lines(DATA_PATH)) {
            this.categories = in.distinct().sorted().toList();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot init categories in memory DB", e);
        }
    }
}
