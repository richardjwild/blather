package com.github.richardjwild.blather.io;

import java.util.Scanner;

public class ConsoleInput implements Input {

    private final Scanner scanner;

    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
