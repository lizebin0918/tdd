package com.lzb.conway;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created on : 2024-01-13 20:14
 * @author mac
 */
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

    ConwayGame live(int x, int y) {
        return live(new Cell(x, y));
    }

    void tick() {

        liveToDead(pointsOfLiveToDead());

        deadToLive(pointsOfDeadToLive());

    }

    private void liveToDead(List<Cell> deadPointsByLive) {
        for (Cell cell : deadPointsByLive) {
            cells.getGrid()[cell.y()][cell.x()] = 0;
        }
    }

    private void deadToLive(List<Cell> livePointsByDead) {
        for (Cell cell : livePointsByDead) {
            live(cell);
        }
    }

    public ConwayGame live(Cell cell) {
        cells.getGrid()[cell.y()][cell.x()] = 1;
        return this;
    }

    List<Cell> pointsOfDeadToLive() {
        List<Cell> liveCells = new ArrayList<>();
        for (int i = 0; i < cells.getX(); i++) {
            for (int j = 0; j < cells.getY(); j++) {
                if (isDead(i, j)) {
                    int count = getLiveCount(i, j);
                    if (count == 3) {
                        liveCells.add(new Cell(i, j));
                    }
                }
            }
        }
        return liveCells;
    }

    List<Cell> pointsOfLiveToDead() {

        List<Cell> deadCells = new ArrayList<>();

        for (int i = 0; i < cells.getX(); i++) {
            for (int j = 0; j < cells.getY(); j++) {
                if (isLive(i, j)) {
                    int count = getLiveCount(i, j);
                    if (count < 2) {
                        deadCells.add(new Cell(i, j));
                    }
                    if (count > 3) {
                        deadCells.add(new Cell(i, j));
                    }
                }
            }
        }

        return deadCells;
    }

    boolean isLive(int x, int y) {
        return !isDead(x, y);
    }

    int getLiveCount(int x, int y) {
        return new Cell(x, y).roundPoints().stream().filter(this::isWithin).mapToInt(this::getValue).sum();
    }

    boolean isDead(int x, int y) {
        return cells.getGrid()[y][x] == 0;
    }

    public boolean isWithin(Cell cell) {
        return cell.x() >= 0 && cell.y() >= 0 && cell.x() < cells.getX() && cell.y() < cells.getY();
    }

    public int getValue(Cell cell) {
        if (!isWithin(cell)) {
            return -1;
        }
        return cells.getGrid()[cell.y()][cell.x()];
    }

}
