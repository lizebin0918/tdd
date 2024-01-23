package com.lzb.conway;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created on : 2024-01-13 20:14
 * @author mac
 */
public class ConwayGame {
    private final int x;
    private final int y;

    private final int[][] grid;

    ConwayGame(int x, int y) {
        this.x = x;
        this.y = y;
        grid = new int[y][x];
    }

    ConwayGame(int[][] grid) {
        this.grid = grid;
        this.y = grid.length;
        this.x = grid[0].length;
    }

    int[][] getGrid() {
        return grid;
    }

    ConwayGame live(int x, int y) {
        return live(new Point(x, y));
    }

    void tick() {

        liveToDead(pointsOfLiveToDead());

        deadToLive(pointsOfDeadToLive());

    }

    private void liveToDead(List<Point> deadPointsByLive) {
        for (Point point : deadPointsByLive) {
            grid[point.y()][point.x()] = 0;
        }
    }

    private void deadToLive(List<Point> livePointsByDead) {
        for (Point point : livePointsByDead) {
            live(point);
        }
    }

    public ConwayGame live(Point point) {
        grid[point.y()][point.x()] = 1;
        return this;
    }

    List<Point> pointsOfDeadToLive() {
        List<Point> livePoints = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (isDead(i, j)) {
                    int count = getLiveCount(i, j);
                    if (count == 3) {
                        livePoints.add(new Point(i, j));
                    }
                }
            }
        }
        return livePoints;
    }

    List<Point> pointsOfLiveToDead() {

        List<Point> deadPoints = new ArrayList<>();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (isLive(i, j)) {
                    int count = getLiveCount(i, j);
                    if (count < 2) {
                        deadPoints.add(new Point(i, j));
                    }
                    if (count > 3) {
                        deadPoints.add(new Point(i, j));
                    }
                }
            }
        }

        return deadPoints;
    }

    boolean isLive(int x, int y) {
        return !isDead(x, y);
    }

    int getLiveCount(int x, int y) {
        return new Point(x, y).roundPoints().stream().filter(this::isWithin).mapToInt(this::getValue).sum();
    }

    boolean isDead(int x, int y) {
        return grid[y][x] == 0;
    }

    public boolean isWithin(Point point) {
        return point.x() >= 0 && point.y() >= 0 && point.x() < x && point.y() < y;
    }

    public int getValue(Point point) {
        if (!isWithin(point)) {
            return -1;
        }
        return grid[point.y()][point.x()];
    }

}
