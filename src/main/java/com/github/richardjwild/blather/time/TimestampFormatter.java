package com.github.richardjwild.blather.time;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.SECONDS;

public class TimestampFormatter {

    private final Clock clock;

    public TimestampFormatter(Clock clock) {
        this.clock = clock;
    }

    public String format(Instant timestamp) {
        long secondsDifference = SECONDS.between(timestamp, clock.now());
        return String.format("(%d %s ago)", secondsDifference, pluralize("second", secondsDifference));
    }

    private String pluralize(String unit, long i) {
        return i == 1 ? unit : unit + "s";
    }
}
