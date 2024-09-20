package org.gol.jpasamples.infrastructure.post;

import org.gol.jpaplayground.infrastructure.measure.DbTime;
import org.gol.jpaplayground.infrastructure.post.AutoIdPostGenerator;
import org.gol.jpaplayground.infrastructure.post.PersistablePostGenerator;
import org.gol.jpaplayground.infrastructure.post.PostGenerator;
import org.gol.jpaplayground.infrastructure.post.SequencedIdPostGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.jdbc.batch_size=4"})
class GenerateDataSqlLogTest {

    @Autowired
    PostGenerator postGenerator;
    @Autowired
    PersistablePostGenerator persistablePostGenerator;
    @Autowired
    AutoIdPostGenerator autoIdPostGenerator;
    @Autowired
    SequencedIdPostGenerator sequencedIdPostGenerator;

    private static final Consumer<DbTime> IDLE = t -> {
    };

    @Test
    void generateNonPersistableEntity() {
        postGenerator.generate(5, IDLE);
    }

    @Test
    void generatePersistableEntity() {
        persistablePostGenerator.generate(5, IDLE);
    }

    @Test
    void generateAutoIdEntity() {
        autoIdPostGenerator.generate(5, IDLE);
    }

    @Test
    void generateSequencedIdEntity() {
        sequencedIdPostGenerator.generate(5, IDLE);
    }
}