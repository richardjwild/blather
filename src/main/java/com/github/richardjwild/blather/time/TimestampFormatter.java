package com.github.richardjwild.blather.time;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.apache.commons.lang3.StringUtils.chop;

public class TimestampFormatter {

    private final Clock clock;

    public TimestampFormatter(Clock clock) {
        this.clock = clock;
    }

    public String format(Instant timestamp) {
        long elapsedMinutes = elapsedTimeSince(timestamp, MINUTES);
        if (elapsedMinutes > 0) {
            return formatWithUnit(elapsedMinutes, MINUTES);
        }
        long elapsedSeconds = elapsedTimeSince(timestamp, SECONDS);
        return formatWithUnit(elapsedSeconds, SECONDS);
    }

    private long elapsedTimeSince(Instant then, ChronoUnit unit) {
        return unit.between(then, clock.now());
    }

    private String formatWithUnit(long elapsedTime, ChronoUnit unit) {
        String formattedUnit = formatUnit(unit, elapsedTime);
        return String.format("(%d %s ago)", elapsedTime, formattedUnit);
    }

    private String formatUnit(ChronoUnit unit, long howMany) {
        String unitNamePlural = unit.toString().toLowerCase();
        return howMany == 1 ? chop(unitNamePlural) : unitNamePlural;
    }
}
