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
        boolean[][] indexesToDel = findIndexesInFieldToDel();
        boolean wasSomethingHappened = false;

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (indexesToDel[i][j]) {
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

    private boolean[][] findIndexesInFieldToDel () {
        int sequenceLengthCol;
        boolean[][] indexesToDel = new boolean[FIELD_SIZE][FIELD_SIZE];
        boolean rowsHandling = false;

        GameColor currentColorCol;
        int MIN_ROW_SIZE = 3;
        Point currentPoint;

        for (int i = 0; i < FIELD_SIZE; i++) {
            currentColorCol = GameColor.BLACK;
            sequenceLengthCol = 1;

            for (int j = 0; j < FIELD_SIZE; j++) {

                if (rowsHandling) {
                    currentPoint = field.getPointOn(i, j);
                } else {
                    currentPoint = field.getPointOn(j, i);
                }

                if (currentColorCol.equals(currentPoint.getColor())) {
                    sequenceLengthCol++;

                    if (j == FIELD_SIZE - 1) {
                        for (int k = 1; k <= sequenceLengthCol; k++) {
                            if (rowsHandling) {
                                indexesToDel[i][j - k] = true;
                            } else {
                                indexesToDel[j - k][i] = true;
                            }
                        }
                    }
                } else {
                    if (sequenceLengthCol >= MIN_ROW_SIZE) {
                        for (int k = 1; k <= sequenceLengthCol; k++) {
                            if (rowsHandling) {
                                indexesToDel[i][j - k] = true;
                            } else {
                                indexesToDel[j - k][i] = true;
                            }
                        }
                    }
                    sequenceLengthCol = 1;
                }
                currentColorCol = currentPoint.getColor();

                if (i == FIELD_SIZE - 1 && j == FIELD_SIZE - 1 && !rowsHandling) {
                   rowsHandling = true;
                   i = 0;
                   j = 0;
                }
            }
        }

        return indexesToDel;
    }

    public boolean tryToSwitchPoints(Point pointDrag, Point pointDrop) {
        int tempRowIndex = pointDrop.getRow();
        int tempColumnIndex = pointDrop.getColumn();

        pointDrop.setColumn(pointDrag.getColumn());
        pointDrop.setRow(pointDrag.getRow());

        pointDrag.setRow(tempRowIndex);
        pointDrag.setColumn(tempColumnIndex);

        boolean wasPointsDeleted = replacePoints();

        if (!wasPointsDeleted) {
            pointDrag.setRow(pointDrop.getRow());
            pointDrag.setColumn(pointDrop.getColumn());

            pointDrop.setColumn(tempColumnIndex);
            pointDrop.setRow(tempRowIndex);

            return false;
        } else {
            return true;
        }

    }

}
