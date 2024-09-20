package org.gol.jpaplayground.infrastructure.measure;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;

import java.util.function.Consumer;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class DbTimeService {

    private final Logger log;

    public void runWithMeasure(String operationDesc, Runnable runnable, Consumer<DbTime> dbProcessingTimeCallback) {
        var watch = StopWatch.createStarted();
        runnable.run();
        watch.stop();
        dbProcessingTimeCallback.accept(new DbTime(watch.getTime()));
        log.info("{}: {}", operationDesc, watch.formatTime());
    }

    public <T> T callWithMeasure(String operationDesc, Supplier<T> supplier, Consumer<DbTime> dbProcessingTimeCallback) {
        var watch = StopWatch.createStarted();
        var result = supplier.get();
        watch.stop();
        dbProcessingTimeCallback.accept(new DbTime(watch.getTime()));
        log.info("{}: {}", operationDesc, watch.formatTime());
        return result;
    }
}
