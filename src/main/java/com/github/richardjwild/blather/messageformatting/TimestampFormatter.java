package com.github.richardjwild.blather.messageformatting;

import com.github.richardjwild.blather.time.Clock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.apache.commons.lang3.StringUtils.chop;

public class TimestampFormatter {

    private final Clock clock;

    public TimestampFormatter(Clock clock) {
        this.clock = clock;
    }

    String formatTimestamp(Instant timestamp) {
        long minutesElapsed = howLongAgo(timestamp, MINUTES);
        if (minutesElapsed > 0) {
            return formatWithUnit(minutesElapsed, MINUTES);
        }
        long secondsElapsed = howLongAgo(timestamp, SECONDS);
        return formatWithUnit(secondsElapsed, SECONDS);
    }

    private long howLongAgo(Instant then, ChronoUnit chronoUnit) {
        return chronoUnit.between(then, clock.now());
    }

    private String formatWithUnit(long timeDifference, ChronoUnit chronoUnit) {
        String formattedUnit = formatUnit(chronoUnit, timeDifference);
        return format("(%d %s ago)", timeDifference, formattedUnit);
    }

    private String formatUnit(ChronoUnit unit, long howMany) {
        String unitNamePlural = unit.toString().toLowerCase();
        return howMany == 1 ? chop(unitNamePlural) : unitNamePlural;
    }
}
