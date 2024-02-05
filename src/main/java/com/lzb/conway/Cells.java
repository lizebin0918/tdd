package com.lzb.conway;

import lombok.Getter;

/**
 * 细胞集合<br/>
 * Created on : 2024-02-04 15:59
 *
 * @author lizebin
 */
@Getter
public class Cells {

    private final int x;
    private final int y;
    private final int[][] grid;

    Cells(int x, int y) {
        this.y = y;
        this.x = x;
        grid = new int[y][x];
    }

    Cells(int[][] grid) {
        this.grid = grid;
        this.y = grid.length;
        this.x = grid[0].length;
    }

    public boolean isWithin(int x, int y) {
        return x >= 0 && y >= 0 && x < this.x && y < this.y;
    }

    public boolean isDead(int x, int y) {
        return grid[y][x] == 0;
    }

    public boolean isLive(int x, int y) {
        return !isDead(x, y);
    }

    public int getLiveCount(int x, int y) {
        return new Cell(x, y).roundPoints().stream().filter(cell -> isWithin(cell.x(), cell.y())).mapToInt(this::getValue).sum();
    }

    private int getValue(Cell cell) {
        if (!isWithin(cell.x(), cell.y())) {
            return -1;
        }
        return grid[cell.y()][cell.x()];
    }

    public void live(int x, int y) {
        grid[y][x] = 1;
    }
}
