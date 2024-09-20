package org.gol.jpaplayground.infrastructure.post.relation.manytoone;

import org.gol.jpaplayground.infrastructure.provider.AuthorProvider;
import org.gol.jpaplayground.infrastructure.provider.CategoryProvider;
import org.gol.jpaplayground.infrastructure.provider.SentenceProvider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;

@Component
@RequiredArgsConstructor
class PostMTOAdapter {

    private final AuthorProvider authorProvider;
    private final CategoryProvider categoryProvider;
    private final SentenceProvider sentenceProvider;
    private final PostMTORepository postRepository;
    private final PostCommentMTORepository postCommentRepository;

    @Transactional
    public UUID addPost() {
        return postRepository.save(generatePost())
                .getId();
    }

    @Transactional
    public UUID addComment(UUID postId) {
        return postRepository.findById(postId)
                .map(PostMTOEntity::incrementCommentCount)
                .map(this::createComent)
                .map(postCommentRepository::save)
                .map(PostCommentMTOEntity::getId)
                .orElseThrow();
    }

    @Transactional
    public void addCommentsBulk(UUID postId, int amount) {
        var postEntity = postRepository.findById(postId).orElseThrow();
        var comments = rangeClosed(1, amount)
                .mapToObj(i -> postEntity.incrementCommentCount())
                .map(this::createComent)
                .collect(toSet());
        postCommentRepository.saveAll(comments);
    }

    private PostMTOEntity generatePost() {
        return new PostMTOEntity(authorProvider.drawAuthor(),
                categoryProvider.drawCategory(),
                sentenceProvider.drawSentence(100),
                sentenceProvider.drawSentence(10000));
    }

    PostCommentMTOEntity createComent(PostMTOEntity entity) {
        return new PostCommentMTOEntity(entity, authorProvider.drawAuthor(), sentenceProvider.drawSentence(10000));
    }
}
