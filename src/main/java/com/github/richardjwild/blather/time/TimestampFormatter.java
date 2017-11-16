package com.github.richardjwild.blather.time;

import java.time.Instant;

public class TimestampFormatter {

    public TimestampFormatter(Clock clock) {

    }

    public String format(Instant timestamp) {
        return "(0 seconds ago)";
    }
}
