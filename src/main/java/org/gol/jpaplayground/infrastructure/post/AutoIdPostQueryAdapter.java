package org.gol.jpaplayground.infrastructure.post;

import org.gol.jpaplayground.infrastructure.measure.DbTime;
import org.gol.jpaplayground.infrastructure.measure.DbTimeService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class AutoIdPostQueryAdapter {

    private final AutoIdPostRepository repository;
    private final DbTimeService dbTimeService = new DbTimeService(log);

    public Slice<UUID> findIds(Pageable pageable, Consumer<DbTime> dbProcessingTimeCallback) {
        return dbTimeService.callWithMeasure(
                "Find IDs time",
                () -> repository.findIds(pageable),
                dbProcessingTimeCallback);
    }
    @Transactional
    public Slice<UUID> findIds(Pageable pageable) {
        return repository.findIds(pageable);
    }
}
