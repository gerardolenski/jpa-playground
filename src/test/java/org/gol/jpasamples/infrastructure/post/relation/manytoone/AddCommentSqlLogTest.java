package org.gol.jpasamples.infrastructure.post.relation.manytoone;

import org.gol.jpaplayground.infrastructure.post.relation.manytoone.PostMTOAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import lombok.extern.slf4j.Slf4j;

import static java.util.stream.IntStream.rangeClosed;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.generate_statistics=true",
        "spring.jpa.properties.hibernate.jdbc.batch_size=0"})
class AddCommentSqlLogTest {

    @Autowired
    PostMTOAdapter postAdapter;

    @Test
    void addManyToOneRelatedComment() {
        var postId = postAdapter.addPost();
        rangeClosed(1, 5)
                .mapToObj(i -> postAdapter.addComment(postId))
                .forEach(uuid -> log.info("Added comment: {}", uuid));
    }
}