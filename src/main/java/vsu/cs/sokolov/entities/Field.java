package vsu.cs.sokolov.entities;

import vsu.cs.sokolov.exceptions.FieldException;

public class Field {
    private final int DEFAULT_FIELD_SIZE = 15;
    private Point[][] points;

    public Field(Point[][] points) throws FieldException {

        if (points.length != points[0].length) {
            throw new FieldException("Field size should be as square");
        }
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

    public void setPointOn(int row, int column, Point newPoint) {
        this.points[row][column] = newPoint;
    }
}
