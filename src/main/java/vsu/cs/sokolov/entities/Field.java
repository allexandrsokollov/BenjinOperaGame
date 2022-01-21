package vsu.cs.sokolov.entities;


public class Field {
    private final Point[][] points;
    private static final int DEFAULT_FIELD_SIZE = 15;

    public Field() {

        this.points = new Point[DEFAULT_FIELD_SIZE][DEFAULT_FIELD_SIZE];

        for (int i = 0; i < DEFAULT_FIELD_SIZE; i++) {
            for (int j = 0; j < DEFAULT_FIELD_SIZE; j++) {
                Point tmp = new Point(Point.getPoint(i, j));
                this.points[i][j] = tmp;
            }
        }
    }

    public static int getDefaultFieldSize() {
        return DEFAULT_FIELD_SIZE;
    }

    public Point getPointOn(int row, int column) {
        return points[row][column];
    }

    public Point[][] getPoints() {
        return points;
    }

    public void setPointOn(int row, int column) {
        this.points[row][column] = Point.getPoint(row, column);
    }

}
