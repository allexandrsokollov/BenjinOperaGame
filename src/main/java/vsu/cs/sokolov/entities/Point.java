package vsu.cs.sokolov.entities;

import vsu.cs.sokolov.enums.GameColor;

import java.util.Objects;

public class Point {
    private int x;
    private int y;
    private GameColor color;

    public Point() {
    }

    public Point(int x, int y, GameColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public static Point getPoint(int x, int y) {
        return new Point(x, y, GameColor.getRandColor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return color.equals(((Point) o).getColor());
    }

    public boolean isFullyEquals(Object o) {
        if (this == o) return true;
        if (!(o instanceof  Point point)) return false;
        return x == ((Point) o).getX() && y == ((Point) o).getY() && color.equals(((Point) o).getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, color);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public GameColor getColor() {
        return color;
    }

    public void setColor(GameColor color) {
        this.color = color;
    }
}
