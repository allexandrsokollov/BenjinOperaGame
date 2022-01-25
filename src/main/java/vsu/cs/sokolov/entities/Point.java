package vsu.cs.sokolov.entities;

import vsu.cs.sokolov.enums.GameColor;

import java.util.Objects;

public class Point {
    private final int columnIndex;
    private final int rowIndex;
    private GameColor color;

    public Point(int row, int column, GameColor color) {
        this.columnIndex = column;
        this.rowIndex = row;
        this.color = color;
    }

    public Point(Point point) {
        this.columnIndex = point.getColumnIndex();
        this.rowIndex = point.getRowIndex();
        this.color = point.getColor();
    }

    public static Point getPoint(int x, int y) {
        return new Point(x, y, GameColor.getRandColor());
    }

    public boolean isPointBeside(Point point) {

        if (Math.abs(this.columnIndex - point.getColumnIndex()) == 1 &&
                Math.abs(this.rowIndex - point.getRowIndex()) == 0) {
            return true;

        } else {
            return Math.abs(this.columnIndex - point.getColumnIndex()) == 0 &&
                    Math.abs(this.rowIndex - point.getRowIndex()) == 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        return color.equals(((Point) o).getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnIndex, rowIndex, color);
    }

    @Override
    public String toString() {
        return "" + color;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColor(GameColor color) {
        this.color = color;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public GameColor getColor() {
        return color;
    }
}
