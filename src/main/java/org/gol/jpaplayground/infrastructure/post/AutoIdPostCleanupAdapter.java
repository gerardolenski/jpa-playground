package org.gol.jpaplayground.infrastructure.post;

import org.gol.jpaplayground.infrastructure.measure.DbTime;
import org.gol.jpaplayground.infrastructure.measure.DbTimeService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class AutoIdPostCleanupAdapter {

    private final AutoIdPostRepository repository;
    private final DbTimeService dbTimeService = new DbTimeService(log);

    public void deleteAll(Consumer<DbTime> dbProcessingTimeCallback) {
        dbTimeService.runWithMeasure(
                "Deleting time",
                repository::deleteAll,
                dbProcessingTimeCallback);
    }

    public void deleteAllInBatch(Consumer<DbTime> dbProcessingTimeCallback) {
        dbTimeService.runWithMeasure(
                "Deleting time",
                repository::deleteAllInBatch,
                dbProcessingTimeCallback);
    }

    public void deleteAllById(Iterable<UUID> ids, Consumer<DbTime> dbProcessingTimeCallback) {
        dbTimeService.runWithMeasure(
                "Deleting time",
                () -> repository.deleteAllById(ids),
                dbProcessingTimeCallback);
    }

    public void deleteAllByIdInBatch(Iterable<UUID> ids, Consumer<DbTime> dbProcessingTimeCallback) {
        dbTimeService.runWithMeasure(
                "Deleting time",
                () -> repository.deleteAllByIdInBatch(ids),
                dbProcessingTimeCallback);
    }

    @Transactional
    public void deleteAllByIdInBatch(Iterable<UUID> ids) {
        repository.deleteAllByIdInBatch(ids);
    }
}
