package vsu.cs.sokolov.entities;

public class Field {
    private final int DEFAULT_FIELD_SIZE = 15;
    private Point[][] points;

    public Field(Point[][] points) {
        this.points = points;
    }

    public Point getPointOn(int column, int row) {
        return points[column][row];
    }

    public Point[][] getPoints() {
        return points;
    }

    public void setPoints(Point[][] points) {
        this.points = points;
    }
}
