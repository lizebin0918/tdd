package com.lzb.conway;

import java.util.ArrayList;
import java.util.List;

import static com.lzb.conway.Cell.Direction.DOWN;
import static com.lzb.conway.Cell.Direction.LEFT;
import static com.lzb.conway.Cell.Direction.RIGHT;
import static com.lzb.conway.Cell.Direction.UP;

/**
 * <br/>
 * Created on : 2024-01-14 16:32
 *
 * @author mac
 */
public record Cell(int x, int y) {

    public static Cell of(int x, int y) {
        return new Cell(x, y);
    }

    public Cell next(Direction direction) {
        return switch (direction) {
            case UP -> Cell.of(x, y - 1);
            case DOWN -> Cell.of(x, y + 1);
            case LEFT -> Cell.of(x - 1, y);
            case RIGHT -> Cell.of(x + 1, y);
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
    List<Cell> roundPoints() {
        List<Cell> cells = new ArrayList<>();
        cells.add(next(LEFT).next(UP));
        cells.add(next(LEFT));
        cells.add(next(LEFT).next(DOWN));
        cells.add(next(DOWN));
        cells.add(next(RIGHT).next(DOWN));
        cells.add(next(RIGHT));
        cells.add(next(RIGHT).next(UP));
        cells.add(next(UP));
        return cells;
    }

}
