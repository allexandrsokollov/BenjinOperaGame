package vsu.cs.sokolov.entities;

import vsu.cs.sokolov.enums.GameColor;

import java.util.Objects;

public class Point {
    private final int x;
    private final int y;
    private final GameColor color;

    public Point(int x, int y, GameColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Point(Point point) {
        this.x = point.getX();
        this.y = point.getY();
        this.color = point.getColor();
    }

    public static Point getPoint(int x, int y) {
        return new Point(x, y, GameColor.getRandColor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        return color.equals(((Point) o).getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, color);
    }

    @Override
    public String toString() {
        return "" + y;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public GameColor getColor() {
        return color;
    }
}
