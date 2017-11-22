package com.github.richardjwild.blather.messageformatting;

import com.github.richardjwild.blather.datatransfer.Message;

import java.util.StringJoiner;

public class WallMessageFormatter {

    private final ReadMessageFormatter readMessageFormatter;

    public WallMessageFormatter(ReadMessageFormatter readMessageFormatter) {
        this.readMessageFormatter = readMessageFormatter;
    }

    public String format(Message message) {
        return new StringJoiner(" - ")
                .add(message.recipient.toString())
                .add(readMessageFormatter.format(message))
                .toString();
    }
}
