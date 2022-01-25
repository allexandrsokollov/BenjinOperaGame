package vsu.cs.sokolov.entities;

import vsu.cs.sokolov.enums.GameColor;

public class Game {
    private final Field field;
    private int amountOfNewPoints;
    private int score;
    private final int FIELD_SIZE;
    private boolean gameIsGoing;

    public Game(int level) {
        this.field = new Field();
        this.FIELD_SIZE = field.getPoints().length;
        this.score = 0;
        this.amountOfNewPoints = level * 10;
        this.gameIsGoing = false;
    }

    public int replacePoints() {
        Boolean[][] indexesToDel = findIndexesInFieldToDel();
        int amountOfReplacedPoints = 0;
        int droppedPoints = 1;

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (indexesToDel[i][j]) {
                    field.setDeletedPoint(i, j);
                    amountOfReplacedPoints++;
                }
            }
        }

        while (droppedPoints > 0) {
            droppedPoints = dropPoints();
        }

        if (gameIsGoing) {
            amountOfNewPoints -= amountOfReplacedPoints;
            score += amountOfReplacedPoints;
        }

        if (amountOfNewPoints < 0) {
            return -1;
        }

        return amountOfReplacedPoints;
    }

    public int dropPoints() {
        int amountOfDroppedElems = 0;
//        for (int i = 0; i < FIELD_SIZE - 1; i++) {
//            for (int j = 0; j < FIELD_SIZE; j++) {
//
//                if (field.getPointOn(j, i + 1).getColor().equals(GameColor.BLACK) &&
//                    !field.getPointOn(j, i).getColor().equals(GameColor.BLACK)) {
//
//                    field.getPointOn(j, i + 1).setColor(field.getPointOn(j, i).getColor());
//                    field.getPointOn(j, i).setColor(GameColor.BLACK);
//                    amountOfDroppedElems++;
//                }
//            }
//        }

        for (int i = 0; i < FIELD_SIZE - 1; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field.getPointOn(i + 1, j).getColor().equals(GameColor.BLACK) &&
                !field.getPointOn(i, j).getColor().equals(GameColor.BLACK)) {

                    field.getPointOn(i + 1, j).setColor(field.getPointOn(i, j).getColor());
                    field.getPointOn(i, j).setColor(GameColor.BLACK);
                    amountOfDroppedElems++;
                }
            }
        }

        for (int i = 0; i < FIELD_SIZE; i++) {
            if (field.getPointOn(0, i).getColor().equals(GameColor.BLACK)) {
                field.getPointOn(0, i).setColor(GameColor.DELETED);
                amountOfDroppedElems++;
            }
        }

        return amountOfDroppedElems;
    }
    public int getScore() {
        return score;
    }

    public void setGameIsGoing(boolean gameIsGoing) {
        this.gameIsGoing = gameIsGoing;
    }


    public void setLevel(int level) {
        this.amountOfNewPoints = level * 100;
    }

    public int getAmountOfNewPoints() {
        return amountOfNewPoints;
    }

    public Field getField() {
        return field;
    }

    private Boolean[][] findIndexesInFieldToDel () {
        int sequenceLengthCol;
        Boolean[][] indexesToDel = new Boolean[FIELD_SIZE][FIELD_SIZE];

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                indexesToDel[i][j] = Boolean.FALSE;
            }
        }
        boolean rowsHandling = false;

        GameColor currentColorCol;
        final int MIN_ROW_SIZE = 3;
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

                if (currentColorCol.equals(currentPoint.getColor()) && !currentPoint.getColor().equals(GameColor.BLACK)) {

                    if (!(currentColorCol.equals(GameColor.DELETED) || currentPoint.getColor().equals(GameColor.DELETED))) {
                        sequenceLengthCol++;
                    }

                    if (j == FIELD_SIZE - 1) {

                        if (sequenceLengthCol >= MIN_ROW_SIZE) {
                            findBoolPointsToDel(sequenceLengthCol, indexesToDel, i, j, rowsHandling);
                        }
                        sequenceLengthCol = 1;
                    }
                } else {

                    if (sequenceLengthCol >= MIN_ROW_SIZE) {
                        findBoolPointsToDel(sequenceLengthCol, indexesToDel, i, j, rowsHandling);
                    }
                    sequenceLengthCol = 1;
                }
                currentColorCol = currentPoint.getColor();

                if (!rowsHandling && i == FIELD_SIZE - 1 && j == FIELD_SIZE - 1) {
                    rowsHandling = true;
                    currentColorCol = GameColor.BLACK;
                    i = 0;
                    j = 0;
                }
            }
        }
        return indexesToDel;
    }

    private void findBoolPointsToDel(Integer sequenceLengthCol, Boolean[][] indexesToDel, int i, int j, boolean rowsHandling) {
        for (int k = 1; k <= sequenceLengthCol; k++) {

            if (j - k >= 0) {
                if (rowsHandling) {
                    indexesToDel[i][j - k] = Boolean.TRUE;
                } else {
                    indexesToDel[j - k][i] = Boolean.TRUE;
                }
            }
        }
    }

    public void switchPoints(Point pointDrag, Point pointDrop) {
        int dropRowIndex = pointDrop.getRowIndex();
        int dropColumnIndex = pointDrop.getColumnIndex();

        int dragRowIndex = pointDrag.getRowIndex();
        int dragColumnIndex = pointDrag.getColumnIndex();

        GameColor drag = field.getPointOn(dragRowIndex,dragColumnIndex ).getColor();
        GameColor drop = field.getPointOn(dropRowIndex, dropColumnIndex).getColor();

        field.updatePointOn(dragRowIndex, dragColumnIndex, drop);
        field.updatePointOn(dropRowIndex,dropColumnIndex, drag);

        int wasPointsDeleted = replacePoints();

        if (wasPointsDeleted == 0) {
            field.updatePointOn(dragRowIndex,dragColumnIndex,  drag);
            field.updatePointOn(dropRowIndex,dropColumnIndex,  drop);
        }
//        while (wasPointsDeleted > 0) {
//            wasPointsDeleted = replacePoints();
//        }
    }
}
