package com.github.richardjwild.blather.time;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

public class TimestampFormatter {

    private static final String FORMAT_SPECIFIER = "(%d %s ago)";

    private final Clock clock;

    public TimestampFormatter(Clock clock) {
        this.clock = clock;
    }

    public String format(Instant timestamp) {
        long minutesElapsed = howLongAgo(timestamp, MINUTES);
        if (minutesElapsed > 0) {
            return format(minutesElapsed, MINUTES);
        }
        long secondsElapsed = howLongAgo(timestamp, SECONDS);
        return format(secondsElapsed, SECONDS);
    }

    private long howLongAgo(Instant then, ChronoUnit chronoUnit) {
        return chronoUnit.between(then, clock.now());
    }

    private String format(long timeDifference, ChronoUnit chronoUnit) {
        return String.format(FORMAT_SPECIFIER, timeDifference, pluralize(chronoUnit, timeDifference));
    }

    private String pluralize(ChronoUnit unit, long i) {
        String unitNamePlural = unit.toString().toLowerCase();
        return i == 1 ? stripLastCharacter(unitNamePlural) : unitNamePlural;
    }

    private String stripLastCharacter(String s) {
        return s.substring(0, s.length() - 1);
    }
}
