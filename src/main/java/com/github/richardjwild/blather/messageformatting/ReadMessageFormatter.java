package com.github.richardjwild.blather.messageformatting;

import com.github.richardjwild.blather.datatransfer.Message;

import java.util.StringJoiner;

public class ReadMessageFormatter {

    private final TimestampFormatter timestampFormatter;

    public ReadMessageFormatter(TimestampFormatter timestampFormatter) {
        this.timestampFormatter = timestampFormatter;
    }

    public String format(Message message) {
        return new StringJoiner(" ")
                .add(message.text())
                .add(timestampFormatter.formatTimestamp(message.timestamp()))
                .toString();
    }
}
