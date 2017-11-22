package com.github.richardjwild.blather.messageformatting;

import com.github.richardjwild.blather.datatransfer.Message;

public interface MessageFormatter {

    String format(Message message);
}
