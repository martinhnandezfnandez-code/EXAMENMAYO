package com.examenmayo.console.input;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public int readInt() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String readString() {
        return scanner.nextLine().trim();
    }

    public int readIntInRange(int min, int max) {
        int value = readInt();
        if (value < min || value > max) {
            return -1;
        }
        return value;
    }

    public void waitForEnter() {
        scanner.nextLine();
    }
}
