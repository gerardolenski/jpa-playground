package org.gol.jpasamples.infrastructure.post;

import org.gol.jpaplayground.infrastructure.measure.DbStats;
import org.gol.jpaplayground.infrastructure.post.AutoIdPostGenerator;
import org.gol.jpaplayground.infrastructure.post.PersistablePostGenerator;
import org.gol.jpaplayground.infrastructure.post.PostGenerator;
import org.gol.jpaplayground.infrastructure.post.SequencedIdPostGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.stream.IntStream.rangeClosed;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.hikari.maximum-pool-size=200",
        "spring.jpa.properties.hibernate.generate_statistics=false"})
class GenerateDataConcurrentTest {

    @Autowired
    PostGenerator postGenerator;
    @Autowired
    PersistablePostGenerator persistablePostGenerator;
    @Autowired
    AutoIdPostGenerator autoIdPostGenerator;
    @Autowired
    SequencedIdPostGenerator sequencedIdPostGenerator;

    @Test
    void generatePersistableEntity200k() {
        var stats = new DbStats();
        var executor = newFixedThreadPool(2);

        rangeClosed(1, 20)
                .mapToObj(i -> runAsync(() -> persistablePostGenerator.generate(10000, stats::consume), executor))
                .reduce(CompletableFuture::allOf)
                .ifPresent(CompletableFuture::join);

        stats.doLog();
    }

    @Test
    void generateSequencedIDEntity200k() {
        var stats = new DbStats();
        var executor = newFixedThreadPool(2);

        rangeClosed(1, 20)
                .mapToObj(i -> runAsync(() -> sequencedIdPostGenerator.generate(10000, stats::consume), executor))
                .reduce(CompletableFuture::allOf)
                .ifPresent(CompletableFuture::join);

        stats.doLog();
    }

    @Test
    void generatePersistableEntity100k50threads() {
        var stats = new DbStats();
        var executor = newFixedThreadPool(50);

        rangeClosed(1, 100000)
                .mapToObj(i -> runAsync(() -> persistablePostGenerator.generate(1, stats::consume), executor))
                .reduce(CompletableFuture::allOf)
                .ifPresent(CompletableFuture::join);

        stats.doLog();
    }

    @Test
    void generateSequencedIDEntity100k50threads() {
        var stats = new DbStats();
        var executor = newFixedThreadPool(50);

        rangeClosed(1, 100000)
                .mapToObj(i -> runAsync(() -> sequencedIdPostGenerator.generate(1, stats::consume), executor))
                .reduce(CompletableFuture::allOf)
                .ifPresent(CompletableFuture::join);

        stats.doLog();
    }

    @Test
    void generateAutoIdEntity1M() {
        var stats = new DbStats();
        var executor = newFixedThreadPool(4);

        rangeClosed(1, 1000)
                .mapToObj(i -> runAsync(() -> autoIdPostGenerator.generate(1000, stats::consume), executor))
                .reduce(CompletableFuture::allOf)
                .ifPresent(CompletableFuture::join);

        stats.doLog();
    }
}