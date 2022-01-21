package vsu.cs.sokolov.entities;

import vsu.cs.sokolov.enums.GameColor;

import java.util.Objects;

public class Point {
    private int columnIndex;
    private int rowIndex;
    private GameColor color;

    public Point(int column, int row, GameColor color) {
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
        return Math.abs(this.columnIndex - point.getColumnIndex()) == 1 || Math.abs(this.rowIndex - point.getRowIndex()) == 1;
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

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void setColor(GameColor color) {
        this.color = color;
    }

    public void setColAndRowIndexes(int rowIndex, int columnIndex) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public GameColor getColor() {
        return color;
    }
}
