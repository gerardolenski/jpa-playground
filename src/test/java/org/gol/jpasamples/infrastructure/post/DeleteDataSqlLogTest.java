package org.gol.jpasamples.infrastructure.post;

import org.gol.jpaplayground.infrastructure.post.AutoIdPostGenerator;
import org.gol.jpaplayground.infrastructure.post.AutoIdPostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.data.domain.Pageable.ofSize;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.generate_statistics=true",
        "spring.jpa.properties.hibernate.jdbc.batch_size=0"})
class DeleteDataSqlLogTest {

    @Autowired
    private AutoIdPostRepository repository;
    @Autowired
    private AutoIdPostGenerator generator;

    @Test
    void deleteAll() {
        log.info("Inserting entities");
        generator.generate(10, t -> {});

        log.info("Deleting entities");
        repository.deleteAll();
    }

    @Test
    void deleteAllInBatch() {
        log.info("Inserting entities");
        generator.generate(10, t -> {});

        log.info("Deleting entities");
        repository.deleteAllInBatch();
    }

    @Test
    void deleteAllById() {
        log.info("Inserting entities");
        generator.generate(5, t -> {});

        log.info("Getting IDS");
        var pageable = ofSize(500);
        var ids = repository.findIds(pageable);

        log.info("Deleting entities");
        repository.deleteAllById(ids);
    }

    @Test
    void deleteAllByIdInBatch() {
        log.info("Inserting entities");
        generator.generate(10, t -> {});

        log.info("Getting IDS");
        var pageable = ofSize(500);
        var ids = repository.findIds(pageable);

        log.info("Deleting entities");
        repository.deleteAllByIdInBatch(ids);
    }
}
