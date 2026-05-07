package com.examenmayo.console.renderer;

import java.util.List;

public class ConsoleRenderer {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";
    private static final int WIDTH = 60;

    public void clear() {
        for (int i = 0; i < 50; i++) System.out.println();
    }

    public void renderTitle(String title) {
        String border = "#".repeat(WIDTH);
        System.out.println(border);
        int padding = (WIDTH - title.length()) / 2;
        System.out.println("#" + " ".repeat(padding - 1) + CYAN + BOLD + title + RESET + " ".repeat(WIDTH - padding - title.length() - 1) + "#");
        System.out.println(border);
    }

    public void renderMenu(List<String> options, String title) {
        renderTitle(title);
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("  %s%d.%s %s%n", YELLOW, i + 1, RESET, options.get(i));
        }
        System.out.println("#".repeat(WIDTH));
        System.out.print("  " + GREEN + "Selecciona una opción: " + RESET);
    }

    public void renderHeader(String text) {
        System.out.println("#".repeat(WIDTH));
        System.out.println("# " + BOLD + text + RESET + " ".repeat(WIDTH - text.length() - 3) + "#");
        System.out.println("#".repeat(WIDTH));
    }

    public void renderInfo(String label, String value) {
        System.out.printf("  " + CYAN + "%-20s" + RESET + ": %s%n", label, value);
    }

    public void renderStatBar(String label, int current, int max) {
        int barLength = 20;
        int filled = (int) ((double) current / max * barLength);
        String bar = "█".repeat(Math.max(0, filled)) + "░".repeat(Math.max(0, barLength - filled));
        String color = current < max * 0.3 ? RED : GREEN;
        System.out.printf("  %-12s " + color + "%s" + RESET + " %d/%d%n", label, bar, current, max);
    }

    public void renderSeparator() {
        System.out.println("-".repeat(WIDTH));
    }

    public void renderError(String message) {
        System.out.println("  " + RED + "✖ " + message + RESET);
    }

    public void renderSuccess(String message) {
        System.out.println("  " + GREEN + "✔ " + message + RESET);
    }

    public void renderBox(String... lines) {
        int maxLen = 0;
        for (String line : lines) {
            if (line.length() > maxLen) maxLen = line.length();
        }
        int boxWidth = Math.max(maxLen + 4, WIDTH - 4);
        System.out.println("  ┌" + "─".repeat(boxWidth) + "┐");
        for (String line : lines) {
            System.out.println("  │ " + line + " ".repeat(boxWidth - line.length() - 1) + "│");
        }
        System.out.println("  └" + "─".repeat(boxWidth) + "┘");
    }

    public void renderCombatLog(String log) {
        System.out.println("  " + PURPLE + "⚔ " + log + RESET);
    }

    public void renderHealLog(String log) {
        System.out.println("  " + GREEN + "♥ " + log + RESET);
    }

    public void renderBuffLog(String log) {
        System.out.println("  " + BLUE + "♦ " + log + RESET);
    }

    public void renderDamage(int cantidad) {
        System.out.println("  " + RED + "💥 Daño: " + cantidad + RESET);
    }

    public void pressAnyKey() {
        System.out.print("  " + YELLOW + "Presiona Enter para continuar..." + RESET);
    }
}
