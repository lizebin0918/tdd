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


}
