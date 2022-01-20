package vsu.cs.sokolov.entities;

import vsu.cs.sokolov.enums.GameColor;

public class Game {
    private Field field;
    private final int FIELD_SIZE;

    public Game(Field field) {
        this.field = field;
        this.FIELD_SIZE = field.getPoints().length;
    }

    public void deletePoints() {
        boolean [][] pointsToDelete = findPointsInRowsToDelete();

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (pointsToDelete[i][j]) {
                    field.setPointOn(i, j, new Point());
                }
            }
        }
    }

    public Field getField() {
        return field;
    }

    private boolean[][] findPointsInRowsToDelete() {
        int sequenceLengthRow = 0;
        int sequenceLengthCol = 0;
        boolean[][] indexesOfPointsToDel = new boolean[field.getPoints().length][field.getPoints()[0].length];

        GameColor currentColorRow = GameColor.BLACK;
        Point currentPointRow;

        GameColor currentColorCol = GameColor.BLACK;
        Point currentPointCol;
        int MIN_ROW_SIZE = 3;

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {

                currentPointRow = field.getPointOn(i, j);

                if (currentColorRow.equals(currentPointRow.getColor())) {
                    sequenceLengthRow++;

                } else if (sequenceLengthRow >= MIN_ROW_SIZE) {

                    for (int k = 1; k <= sequenceLengthRow; k++) {
                        indexesOfPointsToDel[i][j - k] = true;
                    }
                    currentColorRow = currentPointRow.getColor();
                    sequenceLengthRow = 0;

                } else {
                    currentColorRow = currentPointRow.getColor();
                    sequenceLengthRow = 0;
                }

                currentPointCol = field.getPointOn(j, i);

                if (currentColorCol.equals(currentPointCol.getColor())) {
                    sequenceLengthCol++;

                } else if (sequenceLengthCol >= MIN_ROW_SIZE) {

                    for (int k = 1; k <= sequenceLengthCol; k++) {
                        indexesOfPointsToDel[j][i - k] = true;
                    }
                    currentColorCol = currentPointCol.getColor();
                    sequenceLengthCol = 0;

                } else {
                    currentColorCol = currentPointCol.getColor();
                    sequenceLengthCol = 0;
                }

            }
        }


        return indexesOfPointsToDel;
    }

}
