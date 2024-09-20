package org.gol.jpasamples.infrastructure.post.relation.onetomany;

import org.gol.jpaplayground.infrastructure.measure.DbStats;
import org.gol.jpaplayground.infrastructure.measure.DbTime;
import org.gol.jpaplayground.infrastructure.measure.DbTimeService;
import org.gol.jpaplayground.infrastructure.post.relation.onetomany.PostCommentOTMRepository;
import org.gol.jpaplayground.infrastructure.post.relation.onetomany.PostOTMAdapter;
import org.gol.jpaplayground.infrastructure.post.relation.onetomany.PostOTMRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;
import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.IntStream.rangeClosed;

@Slf4j
@SpringBootTest
class AddCommentPerfTest {

    @Autowired
    PostOTMRepository postepository;
    @Autowired
    PostCommentOTMRepository postCommentRepository;
    @Autowired
    PostOTMAdapter postAdapter;

    private final DbTimeService dbTimeService = new DbTimeService(log);

    @Test
    void generateTestPopulation() {
        var population = 500_000;
        var bulk = 1_000;

        var postId = postAdapter.addPost();
        rangeClosed(1, population / bulk)
                .forEach(i -> postAdapter.addCommentsBulk(postId, bulk));

        log.info("Added comments to post: id={}", postId);
    }

    @Test
    void delete() {
        postCommentRepository.deleteAllInBatch();
        postepository.deleteAllInBatch();
    }

    @Test
    void addManyToOneRelatedComments50k() {
        var postId = postAdapter.addPost();
        var population = 10_000;
        var stats = new DbStats();

        rangeClosed(1, population)
                .mapToObj(i -> addComment(postId, stats::consume))
                .forEach(uuid -> log.info("Added comment: {}", uuid));

        stats.doLog();
    }

    @Test
    void add10MoreCommentsTo500kPopulation() {
        var postId = UUID.fromString("c942a241-1489-4174-a965-dba560dbcaf1");
        var population = 10;
        var stats = new DbStats();

        rangeClosed(1, population)
                .mapToObj(i -> addComment(postId, stats::consume))
                .forEach(uuid -> log.info("Added comment: {}", uuid));

        stats.doLog();
    }

    private UUID addComment(UUID postId, Consumer<DbTime> dbProcessingTimeCallback) {
        return dbTimeService.callWithMeasure("Adding comment time",
                () -> postAdapter.addComment(postId),
                dbProcessingTimeCallback);
    }

}