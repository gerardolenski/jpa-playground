package org.gol.jpasamples.infrastructure.post;

import org.gol.jpaplayground.infrastructure.measure.DbStats;
import org.gol.jpaplayground.infrastructure.measure.DbTime;
import org.gol.jpaplayground.infrastructure.measure.DbTimeService;
import org.gol.jpaplayground.infrastructure.post.AutoIdPostCleanupAdapter;
import org.gol.jpaplayground.infrastructure.post.AutoIdPostGenerator;
import org.gol.jpaplayground.infrastructure.post.AutoIdPostQueryAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.stream.IntStream.rangeClosed;
import static org.springframework.data.domain.Pageable.ofSize;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.jdbc.batch_size=0"})
class DeleteDataTest {

    private static final Consumer<DbTime> IDLE = t -> {
    };

    private final DbTimeService dbTimeService = new DbTimeService(log);

    @Autowired
    private AutoIdPostCleanupAdapter cleanupAdapter;
    @Autowired
    private AutoIdPostGenerator generator;
    @Autowired
    private AutoIdPostQueryAdapter queryAdapter;

    @Test
    void generateTestPopulation() {
        var population = 500_000;
        var bulk = 1_000;
        var executor = newFixedThreadPool(4);
        var stats = new DbStats();

        rangeClosed(1, population / bulk)
                .mapToObj(i -> runAsync(() -> generator.generate(bulk, stats::consume), executor))
                .reduce(CompletableFuture::allOf)
                .ifPresent(CompletableFuture::join);

        stats.doLog();
    }

    @Test
    void deleteAll() {
        var stats = new DbStats();
        cleanupAdapter.deleteAll(stats::consume);
        stats.doLog();
    }

    @Test
    void deleteAllInBatch() {
        var stats = new DbStats();
        cleanupAdapter.deleteAllInBatch(stats::consume);
        stats.doLog();
    }

    @Test
    void deleteAllById() {
        var stats = new DbStats();
        var pageable = ofSize(1000);

        Stream.generate(() -> queryAdapter.findIds(pageable, IDLE))
                .takeWhile(Slice::hasContent)
                .forEach(ids -> cleanupAdapter.deleteAllById(ids, stats::consume));

        stats.doLog();
    }

    @Test
    void deleteAllByIdInBatch() {
        var stats = new DbStats();
        var pageable = ofSize(1000);

        Stream.generate(() -> queryAdapter.findIds(pageable, IDLE))
                .takeWhile(Slice::hasContent)
                .forEach(ids -> cleanupAdapter.deleteAllByIdInBatch(ids, stats::consume));

        stats.doLog();
    }

    @Test
    void deleteAllByIdInBatchBulkStrategy() {
        var bulk = ofSize(1000);
        Stream.generate(() -> queryAdapter.findIds(bulk))
                .takeWhile(Slice::hasContent)
                .forEach(ids -> cleanupAdapter.deleteAllByIdInBatch(ids));
    }
}
