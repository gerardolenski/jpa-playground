package org.gol.jpaplayground.infrastructure.measure;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Long.MAX_VALUE;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDurationHMS;

@Slf4j
public class DbStats {

    private final AtomicLong dbTimeSum = new AtomicLong();
    private final AtomicLong transactionCounter = new AtomicLong();
    private final AtomicLong transactionMinTime = new AtomicLong(MAX_VALUE);
    private final AtomicLong transactionMaxTime = new AtomicLong();
    private final StopWatch watch = StopWatch.createStarted();

    public void consume(DbTime dbTime) {
        var millis = dbTime.millis();
        dbTimeSum.addAndGet(millis);
        transactionCounter.incrementAndGet();
        transactionMinTime.getAndUpdate(min -> millis < min ? millis : min);
        transactionMaxTime.getAndUpdate(max -> millis > max ? millis : max);
    }

    public void doLog() {
        watch.stop();
        var dbTime = dbTimeSum.get();
        var transactionCount = transactionCounter.get();
        log.info("OVERALL execution time: {}", watch.formatTime());
        log.info("DB processing time: {}", formatDurationHMS(dbTime));
        if (transactionCount > 0) {
            log.info("Transactions: count={}, avgTime={}, minTime={}, maxTime={}",
                    transactionCount,
                    formatDurationHMS(dbTime / transactionCount),
                    formatDurationHMS(transactionMinTime.get()),
                    formatDurationHMS(transactionMaxTime.get()));
        }
    }
}
