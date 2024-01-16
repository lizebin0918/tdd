package com.lzb.conway;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * <br/>
 * Created on : 2024-01-14 16:32
 * @author mac
 */
public record Point(int x, int y) {

    public Point next(Direction direction) {
        return switch (direction) {
            case UP -> new Point(x, y - 1);
            case DOWN -> new Point(x, y + 1);
            case LEFT -> new Point(x - 1, y);
            case RIGHT -> new Point(x + 1, y);
        };
    }

    Optional<Point> within(Predicate<Point> isWithinPredicate) {
        boolean isWithin = isWithinPredicate.test(this);
        if (isWithin) {
            return Optional.of(this);
        }
        return Optional.empty();
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }


}
