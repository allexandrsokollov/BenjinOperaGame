package vsu.cs.sokolov.entities;

import vsu.cs.sokolov.enums.GameColor;

public class Game {
    private final Field field;
    private final int FIELD_SIZE;

    public Game() {
        this.field = new Field();
        this.FIELD_SIZE = field.getPoints().length;
    }

    public boolean replacePoints() {
        boolean [][] pointsToDelete = findPointsInRowsToDelete();
        boolean wasSomethingHappened = false;

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (pointsToDelete[i][j]) {
                    field.setPointOn(i, j);
                    wasSomethingHappened =  true;
                }
            }
        }

        return wasSomethingHappened;
    }

    public Field getField() {
        return field;
    }

    private boolean[][] findPointsInRowsToDelete() {
        int sequenceLengthCol;
        boolean[][] indexesOfPointsToDel = new boolean[field.getPoints().length][field.getPoints()[0].length];

        GameColor currentColorCol;
        int MIN_ROW_SIZE = 3;

        for (int i = 0; i < FIELD_SIZE; i++) {
            currentColorCol = GameColor.BLACK;
            sequenceLengthCol = 1;

            for (int j = 0; j < FIELD_SIZE; j++) {
                Point currentPoint = field.getPointOn(i, j);

                if (currentColorCol.equals(currentPoint.getColor())) {
                    sequenceLengthCol++;

                    if (j == FIELD_SIZE - 1) {
                        for (int k = 1; k <= sequenceLengthCol; k++) {
                            indexesOfPointsToDel[i][j - k] = true;
                        }
                    }
                } else {
                    if (sequenceLengthCol >= MIN_ROW_SIZE) {
                        for (int k = 1; k <= sequenceLengthCol; k++) {
                            indexesOfPointsToDel[i][j - k] = true;
                        }
                    }
                    sequenceLengthCol = 1;
                }
                currentColorCol = currentPoint.getColor();
            }
        }

        return indexesOfPointsToDel;
    }

}
