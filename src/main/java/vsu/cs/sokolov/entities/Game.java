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

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (indexesToDel[i][j]) {
                    field.setDeletedPoint(i, j);
                    amountOfReplacedPoints++;

                }
            }
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

//        for (int i = 0; i < FIELD_SIZE; i++) {
//            currentColorCol = GameColor.BLACK;
//            sequenceLengthCol = 1;
//
//            for (int j = 0; j < FIELD_SIZE; j++) {
//
//                if (rowsHandling) {
//                    currentPoint = field.getPointOn(i, j);
//                } else {
//                    currentPoint = field.getPointOn(j, i);
//                }
//
//                if (currentColorCol.equals(currentPoint.getColor())) {
//                    sequenceLengthCol++;
//
//                    if (sequenceLengthCol == 15) {
//                        sequenceLengthCol = 14;
//                    }
//
//                    if (j == FIELD_SIZE - 1) {
//
//                        findBoolPointsToDel(sequenceLengthCol, indexesToDel, i, j);
//
//                        currentColorCol = currentPoint.getColor();
//                        sequenceLengthCol = 1;
//                    }
//                } else {
//
//                    if (sequenceLengthCol >= MIN_ROW_SIZE) {
//                        findBoolPointsToDel(sequenceLengthCol, indexesToDel, i, j);
//                    }
//
//                    sequenceLengthCol = 1;
//                    currentColorCol = currentPoint.getColor();
//                }
//
//                if (i == FIELD_SIZE - 1 && j == FIELD_SIZE - 1 && !rowsHandling) {
//                    rowsHandling = true;
//                    sequenceLengthCol = 1;
//                    currentColorCol = GameColor.BLACK;
//                    i = 0;
//                    j = 0;
//                }
//            }
//        }
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
                    sequenceLengthCol++;

                    if (sequenceLengthCol == 15) {
                        sequenceLengthCol--;
                    }

                    if (j == FIELD_SIZE - 1) {

                        if (sequenceLengthCol >= MIN_ROW_SIZE) {
                            findBoolPointsToDel(sequenceLengthCol, indexesToDel, i, j, rowsHandling);
                        }
                        sequenceLengthCol = 1;
                    }
                } else {

                    if (sequenceLengthCol == 15) {
                        sequenceLengthCol--;
                    }
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

            if (rowsHandling) {
                System.out.println(i + "  " + " J-> " + j + "  " + k);
                indexesToDel[i][j - k] = Boolean.TRUE;
            } else {
                System.out.println(j + " <-J " + "  " + i + "  " + k);
                indexesToDel[j - k][i] = Boolean.TRUE;
            }
        }
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
