package org.gol.jpasamples.infrastructure.post;

import org.gol.jpaplayground.infrastructure.measure.DbStats;
import org.gol.jpaplayground.infrastructure.post.AutoIdPostGenerator;
import org.gol.jpaplayground.infrastructure.post.AutoIdPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.IntStream.rangeClosed;

@Slf4j
@SpringBootTest
class GenerateDataBulkTest {

    @Autowired
    AutoIdPostRepository repository;
    @Autowired
    AutoIdPostGenerator autoIdPostGenerator;

    @BeforeEach
    void deleteDb() {
        repository.deleteAll();
    }

    @Test
    void generate100kOneByOne() {
        var stats = new DbStats();
        rangeClosed(1, 100000)
                .forEach(i -> autoIdPostGenerator.generate(1, stats::consume));
        stats.doLog();
    }

    @Test
    void generate100kBulk10() {
        var stats = new DbStats();
        rangeClosed(1, 10000)
                .forEach(i -> autoIdPostGenerator.generate(10, stats::consume));
        stats.doLog();
    }

    @Test
    void generate100kBulk100() {
        var stats = new DbStats();
        rangeClosed(1, 1000)
                .forEach(i -> autoIdPostGenerator.generate(100, stats::consume));
        stats.doLog();
    }

    @Test
    void generate100kBulk1000() {
        var stats = new DbStats();
        rangeClosed(1, 100)
                .forEach(i -> autoIdPostGenerator.generate(1000, stats::consume));
        stats.doLog();
    }

    @Test
    void generate100kBulk10000() {
        var stats = new DbStats();
        rangeClosed(1, 10)
                .forEach(i -> autoIdPostGenerator.generate(10000, stats::consume));
        stats.doLog();
    }

    @Test
    void generate100kOneTransaction() {
        var stats = new DbStats();
        autoIdPostGenerator.generate(100000, stats::consume);
        stats.doLog();
    }
}