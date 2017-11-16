package com.github.richardjwild.blather.io;

public class ConsoleOutput implements Output {

    @Override
    public void writeLine(String line) {
        System.out.println(line);
    }
}
