package vsu.cs.sokolov.entities;

import vsu.cs.sokolov.enums.GameColor;

import java.util.Objects;

public class Point {
    private int column;
    private int row;
    private final GameColor color;

    public Point(int column, int row, GameColor color) {
        this.column = column;
        this.row = row;
        this.color = color;
    }

    public Point(Point point) {
        this.column = point.getColumn();
        this.row = point.getRow();
        this.color = point.getColor();
    }

    public static Point getPoint(int x, int y) {
        return new Point(x, y, GameColor.getRandColor());
    }
    public boolean isPointBeside(Point point) {
        return Math.abs(this.column - point.getColumn()) == 1 || Math.abs(this.row - point.getRow()) == 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        return color.equals(((Point) o).getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row, color);
    }

    @Override
    public String toString() {
        return "" + row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public GameColor getColor() {
        return color;
    }
}
