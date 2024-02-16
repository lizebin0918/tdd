package com.lzb.conway;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * <br/>
 * Created on : 2024-01-13 20:14
 * @author mac
 */
@Getter
public class ConwayGame {

    private final int[][] grid;
    private final int xBound;
    private final int yBound;

    public ConwayGame(int[][] grid) {
        this.grid = grid;
        this.xBound = grid[0].length - 1;
        this.yBound = grid.length - 1;
    }

    /**
     * 1  8  7
     * 2  xy 6
     * 3  4  5
     * @param x
     * @param y
     * @return
     */
    public int getLiveCount(int x, int y) {
        int count = 0;
        if (isInOfBound(x - 1, y - 1) && grid[y - 1][x - 1] == 1) {
            count++;
        }
        if (isInOfBound(x - 1, y) && grid[y][x - 1] == 1) {
            count++;
        }
        if (isInOfBound(x - 1, y + 1) && grid[y + 1][x - 1] == 1) {
            count++;
        }
        if (isInOfBound(x,y + 1) && grid[y + 1][x] == 1) {
            count++;
        }
        if (isInOfBound(x + 1, y + 1) && grid[y + 1][x + 1] == 1) {
            count++;
        }
        if (isInOfBound(x + 1, y) && grid[y][x + 1] == 1) {
            count++;
        }
        if (isInOfBound(x + 1, y - 1) && grid[y - 1][x + 1] == 1) {
            count++;
        }
        if (isInOfBound(x, y - 1) && grid[y - 1][x] == 1) {
            count++;
        }
        return count;
    }

    public boolean isInOfBound(int x, int y) {
        return x >= 0 && x <= xBound && y >= 0 && y <= yBound;
    }

    public void nextGeneration() {

        List<ImmutablePair<Integer, Integer>> liveToDie = new ArrayList<>();
        List<ImmutablePair<Integer, Integer>> dieToLive = new ArrayList<>();

        for (int x = 0; x <= xBound; x++) {
            for (int y = 0; y <= yBound; y++) {
                int liveCount = getLiveCount( x, y);
                if (grid[y][x] == 1) {
                    if (liveCount < 2 || liveCount > 3) {
                        liveToDie.add(ImmutablePair.of(x, y));
                    }
                } else {
                    if (liveCount == 3) {
                        dieToLive.add(ImmutablePair.of(x, y));
                    }
                }
            }
        }

        liveToDie.forEach(pair -> grid[pair.getRight()][pair.getLeft()] = 0);
        dieToLive.forEach(pair -> grid[pair.getRight()][pair.getLeft()] = 1);
    }
}