package com.lzb.conway;

/**
 * 细胞集合<br/>
 * Created on : 2024-02-04 15:59
 *
 * @author lizebin
 */
public class Cells {

    private final int[][] grid;

    Cells(int x, int y) {
        grid = new int[y][x];
    }

    Cells(int[][] grid) {
        this.grid = grid;
    }


}
