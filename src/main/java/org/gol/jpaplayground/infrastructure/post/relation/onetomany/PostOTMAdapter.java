package org.gol.jpaplayground.infrastructure.post.relation.onetomany;

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
class PostOTMAdapter {

    private final AuthorProvider authorProvider;
    private final CategoryProvider categoryProvider;
    private final SentenceProvider sentenceProvider;
    private final PostOTMRepository postRepository;
    private final PostCommentOTMRepository postCommentRepository;
    private final PostCommentOTMRelatedByIdRepository postCommentRelatedByIdRepository;

    @Transactional
    public UUID addPost() {
        return postRepository.save(generatePost())
                .getId();
    }

    @Transactional
    public UUID addComment(UUID postId) {
        var author = authorProvider.drawAuthor();
        var content = sentenceProvider.drawSentence(10000);
        return postRepository.findById(postId)
                .map(p -> p.addComment(author, content))
                .map(postCommentRepository::save)
                .map(PostCommentOTMEntity::getId)
                .orElseThrow();
    }

    @Transactional
    public void addCommentsBulk(UUID postId, int amount) {
        var comments = rangeClosed(1, amount)
                .mapToObj(i -> new PostCommentOTMRelatedByIdEntity(postId,
                        authorProvider.drawAuthor(),
                        sentenceProvider.drawSentence(10000)))
                .collect(toSet());
        postCommentRelatedByIdRepository.saveAll(comments);
    }

    private PostOTMEntity generatePost() {
        return new PostOTMEntity(authorProvider.drawAuthor(),
                categoryProvider.drawCategory(),
                sentenceProvider.drawSentence(100),
                sentenceProvider.drawSentence(10000));
    }
}
