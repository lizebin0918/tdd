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
                    int count = traversalLive(i, j);
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
                    int count = traversalLive(i, j);
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

    int traversalLive(int x, int y) {
        int count = 0;
        if (getOne(x, y) == 1) {
            count++;
        }
        if (getTwo(x, y) == 1) {
            count++;
        }
        if (getThree(x, y) == 1) {
            count++;
        }
        if (getFour(x, y) == 1) {
            count++;
        }
        if (getFive(x, y) == 1) {
            count++;
        }
        if (getSix(x, y) == 1) {
            count++;
        }
        if (getSeven(x, y) == 1) {
            count++;
        }
        if (getEight(x, y) == 1) {
            count++;
        }
        return count;
    }

    boolean isDead(int x, int y) {
        return grid[y][x] == 0;
    }

    /*
     * 逆时针编号
     * 1 8 7
     * 2 x 6
     * 3 4 5
     * 如果超出边界，返回-1
     */

    public int getOne(int x, int y) {
        if ((x = x - 1) >= 0 && (y = y - 1) >= 0) {
            return grid[y][x];
        }
        return -1;
    }

    public int getTwo(int x, int y) {
        if ((x = x - 1) >= 0) {
            return grid[y][x];
        }
        return -1;
    }

    public int getThree(int x, int y) {
        if ((x = x - 1) >= 0 && (y = y + 1) <= (this.y - 1)) {
            return grid[y][x];
        }
        return -1;
    }

    public int getFour(int x, int y) {
        if ((y = y + 1) <= (this.y - 1)) {
            return grid[y][x];
        }
        return -1;
    }

    public int getFive(int x, int y) {
        if (((x = x + 1) <= (this.x - 1)) && (y = y + 1) <= (this.y - 1)) {
            return grid[y][x];
        }
        return -1;
    }

    public int getSix(int x, int y) {
        if (((x = x + 1) <= (this.x - 1))) {
            return grid[y][x];
        }
        return -1;
    }

    public int getSeven(int x, int y) {
        if (((x = x + 1) <= (this.x - 1)) && (y = y - 1) >= 0) {
            return grid[y][x];
        }
        return -1;
    }

    public int getEight(int x, int y) {
        if ((y = y - 1) >= 0) {
            return grid[y][x];
        }
        return -1;
    }

}
