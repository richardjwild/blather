package com.github.richardjwild.blather;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.io.CommandReader;

public class Blather {

    private final CommandReader commandReader;

    public Blather(CommandReader commandReader) {
        this.commandReader = commandReader;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Blather");
    }

    public void eventLoop() {
        Command command = commandReader.readNextCommand();
        command.execute();
    }
}
