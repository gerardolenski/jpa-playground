package org.gol.jpasamples.infrastructure.post;

import org.gol.jpaplayground.infrastructure.measure.DbStats;
import org.gol.jpaplayground.infrastructure.post.AutoIdPostGenerator;
import org.gol.jpaplayground.infrastructure.post.PersistablePostGenerator;
import org.gol.jpaplayground.infrastructure.post.PostGenerator;
import org.gol.jpaplayground.infrastructure.post.SequencedIdPostGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.IntStream.rangeClosed;

@Slf4j
@SpringBootTest
class GenerateDataTest {

    @Autowired
    PostGenerator postGenerator;
    @Autowired
    PersistablePostGenerator persistablePostGenerator;
    @Autowired
    AutoIdPostGenerator autoIdPostGenerator;
    @Autowired
    SequencedIdPostGenerator sequencedIdPostGenerator;


    @Test
    void generateNonPersistableEntity200k() {
        var stats = new DbStats();
        rangeClosed(1, 20)
                .forEach(i -> postGenerator.generate(10000, stats::consume));
        stats.doLog();
    }

    @Test
    void generatePersistableEntity200k() {
        var stats = new DbStats();
        rangeClosed(1, 20)
                .forEach(i -> persistablePostGenerator.generate(10000, stats::consume));
        stats.doLog();
    }

    @Test
    void generateAutoIdEntity200k() {
        var stats = new DbStats();
        rangeClosed(1, 20)
                .forEach(i -> autoIdPostGenerator.generate(10000, stats::consume));
        stats.doLog();
    }

    @Test
    void generateSequencedIdEntity200k() {
        var stats = new DbStats();
        rangeClosed(1, 20)
                .forEach(i -> sequencedIdPostGenerator.generate(10000, stats::consume));
        stats.doLog();
    }
}