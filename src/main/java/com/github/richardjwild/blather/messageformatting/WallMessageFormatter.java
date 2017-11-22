package com.github.richardjwild.blather.messageformatting;

import com.github.richardjwild.blather.datatransfer.Message;

import java.util.StringJoiner;

public class WallMessageFormatter implements MessageFormatter {

    private final MessageFormatter readMessageFormatter;

    public WallMessageFormatter(MessageFormatter readMessageFormatter) {
        this.readMessageFormatter = readMessageFormatter;
    }

    @Override
    public String format(Message message) {
        return new StringJoiner(" - ")
                .add(message.recipient.toString())
                .add(readMessageFormatter.format(message))
                .toString();
    }
}
