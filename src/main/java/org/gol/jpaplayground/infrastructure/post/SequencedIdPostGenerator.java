package org.gol.jpaplayground.infrastructure.post;

import org.gol.jpaplayground.infrastructure.measure.DbTime;
import org.gol.jpaplayground.infrastructure.measure.DbTimeService;
import org.gol.jpaplayground.infrastructure.provider.AuthorProvider;
import org.gol.jpaplayground.infrastructure.provider.CategoryProvider;
import org.gol.jpaplayground.infrastructure.provider.SentenceProvider;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.IntStream.rangeClosed;

@Slf4j
@Component
@RequiredArgsConstructor
class SequencedIdPostGenerator {

    private final AuthorProvider authorProvider;
    private final CategoryProvider categoryProvider;
    private final SentenceProvider sentenceProvider;
    private final SequencedIdPostRepository postRepository;
    private final DbTimeService dbTimeService = new DbTimeService(log);

    void generate(int amount, Consumer<DbTime> dbProcessingTimeCallback) {
        log.info("Generating posts: amount={}", amount);
        var posts = rangeClosed(1, amount)
                .mapToObj(this::generatePost)
                .collect(toSet());

        log.info("Persisting posts");
        dbTimeService.runWithMeasure("Persisting time",
                () -> postRepository.saveAll(posts),
                dbProcessingTimeCallback);
    }

    private SequencedIdPostEntity generatePost(int i) {
        return new SequencedIdPostEntity(authorProvider.drawAuthor(),
                categoryProvider.drawCategory(),
                sentenceProvider.drawSentence(100),
                sentenceProvider.drawSentence(10000));
    }
}
