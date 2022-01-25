package vsu.cs.sokolov.enums;

import java.util.Random;

public enum GameColor {
    RED, BLUE, YELLOW, PURPLE, ORANGE, BLACK, DELETED;

    public static GameColor getRandColor() {
        Random random = new Random();
        int rand = random.nextInt(0, 4);

        switch (rand) {
            case 0 -> {
                return RED;
            }
            case 1 -> {
                return BLUE;
            }
            case 2 -> {
                return YELLOW;
            }
            case 3 -> {
                return PURPLE;
            }
            case 4 -> {
                return ORANGE;
            }
            default -> {
                return BLACK;
            }
        }
    }
}