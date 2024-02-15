package com.lzb.conway;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2024-01-13 20:14
 * @author mac
 */
@Getter
public class ConwayGame {

    private final Cells cells;

    ConwayGame(int x, int y) {
        cells = new Cells(x, y);
    }

    ConwayGame(int[][] grid) {
        cells = new Cells(grid);
    }

    int[][] getGrid() {
        return cells.getGrid();
    }

    void tick() {
        for (int i = 0; i < cells.getX(); i++) {
            for (int j = 0; j < cells.getY(); j++) {
                int count = cells.liveCountFrom(i, j);
                if (cells.isDead(i, j) && count == 3) {
                    cells.live(i, j);
                }
                if (cells.isLive(i, j) && (count < 2 || count > 3)) {
                    cells.dead(i, j);
                }
            }
        }
    }
}