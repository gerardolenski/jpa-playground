package org.gol.jpasamples.infrastructure.post;

import org.gol.jpaplayground.infrastructure.measure.DbStats;
import org.gol.jpaplayground.infrastructure.post.AutoIdPostGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.IntStream.rangeClosed;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {"spring.jpa.properties.hibernate.jdbc.batch_size=1000"})
class GenerateDataBatch1000Test {

    @Autowired
    AutoIdPostGenerator autoIdPostGenerator;

    @Test
    void generateAutoIdEntity200k() {
        var stats = new DbStats();
        rangeClosed(1, 20)
                .forEach(i -> autoIdPostGenerator.generate(10000, stats::consume));
        stats.doLog();
    }
}