package vsu.cs.sokolov.entities;


public class Field {
    private Point[][] points;
    private static final int DEFAULT_FIELD_SIZE = 15;

    public Field() {

        this.points = new Point[DEFAULT_FIELD_SIZE][DEFAULT_FIELD_SIZE];

        for (int i = 0; i < DEFAULT_FIELD_SIZE; i++) {
            for (int j = 0; j < DEFAULT_FIELD_SIZE; j++) {
                this.points[i][j] = new Point(Point.getPoint(i, j));
            }
        }
    }

    public static int getDefaultFieldSize() {
        return DEFAULT_FIELD_SIZE;
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
