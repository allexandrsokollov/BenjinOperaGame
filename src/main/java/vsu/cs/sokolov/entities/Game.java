package vsu.cs.sokolov.entities;

public class Game {
    private Field field;
    private final int MIN_ROW_SIZE = 3;

    public Game(Field field) {
        this.field = field;
    }

    public boolean deleteRow() {

    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    private boolean[][] findPointsToDelete() {

    }
}
