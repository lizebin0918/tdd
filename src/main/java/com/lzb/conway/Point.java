package com.lzb.conway;

import java.util.ArrayList;
import java.util.List;

import static com.lzb.conway.Point.Direction.DOWN;
import static com.lzb.conway.Point.Direction.LEFT;
import static com.lzb.conway.Point.Direction.RIGHT;
import static com.lzb.conway.Point.Direction.UP;

/**
 * <br/>
 * Created on : 2024-01-14 16:32
 *
 * @author mac
 */
public record Point(int x, int y) {

    public static Point of(int x, int y) {
        return new Point(x, y);
    }

    public Point next(Direction direction) {
        return switch (direction) {
            case UP -> Point.of(x, y - 1);
            case DOWN -> Point.of(x, y + 1);
            case LEFT -> Point.of(x - 1, y);
            case RIGHT -> Point.of(x + 1, y);
        };
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }


    /*
     * 逆时针编号
     * 1 8 7
     * 2 x 6
     * 3 4 5
     */
    List<Point> roundPoints() {
        List<Point> points = new ArrayList<>();
        points.add(next(LEFT).next(UP));
        points.add(next(LEFT));
        points.add(next(LEFT).next(DOWN));
        points.add(next(DOWN));
        points.add(next(RIGHT).next(DOWN));
        points.add(next(RIGHT));
        points.add(next(RIGHT).next(UP));
        points.add(next(UP));
        return points;
    }

}
