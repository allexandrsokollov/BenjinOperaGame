package vsu.cs.sokolov.entities;

import vsu.cs.sokolov.enums.GameColor;

public class Game {
    private final Field field;
    private int level;
    private int amountOfNewPoints;
    private final int FIELD_SIZE;

    public Game(int level) {
        this.field = new Field();
        this.FIELD_SIZE = field.getPoints().length;
        this.level = level;
        this.amountOfNewPoints = level * 1;
    }

    public int replacePoints() {
        boolean[][] indexesToDel = findIndexesInFieldToDel();
        int amountOfReplacedPoints = 0;

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (indexesToDel[i][j]) {
                    field.setPointOn(i, j);
                    amountOfReplacedPoints++;
                }
            }
        }
        amountOfNewPoints -= amountOfReplacedPoints;

        if (amountOfNewPoints < 0) {
            return -1;
        }

        return amountOfReplacedPoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        this.amountOfNewPoints = level * 100;
    }

    public int getAmountOfNewPoints() {
        return amountOfNewPoints;
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

    public void tryToSwitchPoints(Point pointDrag, Point pointDrop) {
        int dropRowIndex = pointDrop.getRowIndex();
        int dropColumnIndex = pointDrop.getColumnIndex();

        int dragRowIndex = pointDrag.getRowIndex();
        int dragColumnIndex = pointDrag.getColumnIndex();

        GameColor drag = field.getPointOn(dragRowIndex, dragColumnIndex).getColor();
        GameColor drop = field.getPointOn(dropRowIndex, dropColumnIndex).getColor();

        field.updatePointOn(dragRowIndex, dragColumnIndex, drop);
        field.updatePointOn(dropRowIndex, dropColumnIndex,drag);


        int wasPointsDeleted = replacePoints();

        if (wasPointsDeleted == 0) {
            field.updatePointOn(dragRowIndex, dragColumnIndex, drag);
            field.updatePointOn(dropRowIndex, dropColumnIndex, drop);
        }


    }

}
