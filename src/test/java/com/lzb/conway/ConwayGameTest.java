package com.lzb.conway;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 只要需求理解没问题，每一步其实都有用的，而不会导致整个工程返工<br/>
 * 如果返工有可能是需求理解错了，或者有另一个思路...tdd好像不能让你找到对的思路...之前的返工有两个原因
 *
 *
 * 0.任务拆解
 * 1.每个任务的实现又可以有很多种方法，容易返工
 * 2.会涌现各种实现思路，不知道用哪个是对的
 *
 * 康威生命游戏是一种模拟细胞生命周期的零玩家游戏。游戏以一个二维的网格作为生命的舞台，每个网格单元都可以是活着的（表示为1）或死亡的（表示为0）。游戏的每一代都根据以下四个规则进行演化：
 *
 * 1. 任何活细胞周围活细胞少于两个，会因为人口稀少而死亡。
 * 2. 任何活细胞周围活细胞超过三个，会因为人口过剩而死亡。
 * 3. 任何活细胞周围有两个或三个活细胞，会继续存活到下一代。
 * 4. 任何死细胞周围恰好有三个活细胞，会在下一代复活。
 *
 * 抽象题目：最终是当前细胞对活细胞的判断
 *
 * Created on : 2024-01-13 20:15
 * @author mac
 */
class ConwayGameTest {

    @Test
    @DisplayName("构建网格")
    void should_init_game() {
        ConwayGame game = new ConwayGame(4, 2);
        assertArrayEquals(new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        }, game.getGrid());
    }

    @Test
    @DisplayName("遍历死细胞变成活细胞")
    void should_live_when_dead_traversal() {
        ConwayGame game = new ConwayGame(new int[][] {
                {1, 0, 1, 0},
                {1, 0, 0, 0}
        });

        List<Cell> liveCells = game.pointsOfDeadToLive();
        assertEquals(2, liveCells.size());
        assertTrue(liveCells.containsAll(List.of(new Cell(1, 0), new Cell(1, 1))));
    }

    @Test
    @DisplayName("遍历获取活细胞数量")
    void should_return_live_count() {
        ConwayGame game = new ConwayGame(new int[][] {
            {1, 0, 1, 0},
            {1, 0, 0, 0}
        });
        assertEquals(3, game.getLiveCount(1, 0));
    }

    @Test
    @DisplayName("如果图形是长方形，获取所有活细胞数量")
    void should_return_live_count_when_game_rectangle() {
        ConwayGame game = new ConwayGame(new int[][] {
                {1, 0, 1, 0},
                {1, 0, 0, 0}
        });

        assertEquals(3, game.getLiveCount(1, 0));
    }

    @Test
    @DisplayName("获取序号1的值")
    void should_return_index1_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(new int[][] {
                {1, 0, 0, 0},
                {0, 0, 0, 0}
        });
        assertEquals(1, game.getLiveCount(1, 1));
    }

    @Test
    @DisplayName("测试point的位移")
    void should_move_point() {
        Cell cell = new Cell(1, 1);
        assertEquals(new Cell(1, 0), cell.next(Cell.Direction.UP));
        assertEquals(new Cell(1, 2), cell.next(Cell.Direction.DOWN));
        assertEquals(new Cell(0, 1), cell.next(Cell.Direction.LEFT));
        assertEquals(new Cell(2, 1), cell.next(Cell.Direction.RIGHT));
        assertEquals(new Cell(0, 0), cell.next(Cell.Direction.UP).next(Cell.Direction.LEFT));
    }

    @Test
    @DisplayName("获取序号2的值")
    void should_return_index2_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(new int[][] {
            {0, 0, 0},
            {1, 0, 0},
            {0, 0, 0}
        });
        assertEquals(1, game.getLiveCount(1, 1));
    }

    @Test
    @DisplayName("获取序号3的值")
    void should_return_index3_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(new int[][] {
            {0, 0, 0},
            {0, 0, 0},
            {1, 0, 0}
        });
        assertEquals(1, game.getLiveCount(1, 1));
    }

    @Test
    @DisplayName("获取序号4的值")
    void should_return_index4_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(new int[][] {
            {0, 0, 0},
            {0, 0, 0},
            {0, 1, 0}
        });
        assertEquals(1, game.getLiveCount(1, 1));
    }

    @Test
    @DisplayName("获取序号5的值")
    void should_return_index5_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(new int[][] {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 1}
        });
        assertEquals(1, game.getLiveCount(1, 1));
    }

    @Test
    @DisplayName("获取序号6的值")
    void should_return_index6_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(new int[][] {
            {0, 0, 0},
            {0, 0, 1},
            {0, 0, 0}
        });
        assertEquals(1, game.getLiveCount(1, 1));
    }

    @Test
    @DisplayName("获取序号7的值")
    void should_return_index7_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(new int[][] {
            {0, 0, 1},
            {0, 0, 0},
            {0, 0, 0}
        });
        assertEquals(1, game.getLiveCount(1, 1));
    }

    @Test
    @DisplayName("获取序号8的值")
    void should_return_index8_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(new int[][] {
            {0, 1, 0},
            {0, 0, 0},
            {0, 0, 0}
        });
        assertEquals(1, game.getLiveCount(1, 1));
    }

    @Test
    @DisplayName("遍历活细胞小于2个的活细胞位置")
    void should_less_2_live_when_live_traversal() {
        ConwayGame game = new ConwayGame(new int[][] {
                {1, 0, 1, 0},
                {1, 0, 0, 0}
        });

        /**
         {1(T), 0(F), 1(T), 0(F)},
         {1(T), 0(F), 0(F), 0(F)}
         **/

        List<Cell> liveCells = game.pointsOfLiveToDead();
        assertEquals(3, liveCells.size());
        assertTrue(liveCells.containsAll(List.of(new Cell(0, 0), new Cell(0, 1), new Cell(2, 0))));
    }

    @Test
    @DisplayName("遍历活细胞大于3个的活细胞位置")
    void should_greater_3_live_when_live_traversal() {
        ConwayGame game = new ConwayGame(new int[][] {
                {1, 1, 1, 0},
                {1, 1, 0, 0}
        });

        /**
         {1(F), 1(T), 1(F), 0(F)},
         {1(F), 1(T), 0(F), 0(F)}
         **/

        List<Cell> liveCells = game.pointsOfLiveToDead();
        assertEquals(2, liveCells.size());
        assertTrue(liveCells.containsAll(List.of(new Cell(1, 1), new Cell(1, 0))));
    }

    @Test
    @DisplayName("测试2*2网格，活细胞灭亡")
    void should_live_to_dead_when_2_2_gird() {
        ConwayGame game = new ConwayGame(new int[][] {
            {1, 0},
            {0, 1}
        });
        game.tick();
        assertArrayEquals(new int[][]{
                {0, 0},
                {0, 0}
        }, game.getGrid());
    }

    @Test
    @DisplayName("测试2*2网格，死细胞存活")
    void should_dead_to_live_when_2_2_gird() {
        ConwayGame game = new ConwayGame(new int[][] {
            {1, 1},
            {1, 0}
        });
        game.tick();
        assertArrayEquals(new int[][]{
                {1, 1},
                {1, 1}
        }, game.getGrid());
    }

    @Test
    @DisplayName("测试2*2网格，无细胞变化")
    void should_no_change_when_2_2_gird() {
        ConwayGame game = new ConwayGame(new int[][] {
            {1, 1},
            {1, 1}
        });
        game.tick();
        assertArrayEquals(new int[][]{
                {1, 1},
                {1, 1}
        }, game.getGrid());
    }

    @Test
    @DisplayName("测试point是否在边界内")
    void should_point_within_grid_bound() {
        ConwayGame game = new ConwayGame(2, 2);
        assertTrue(game.isWithin(0, 0));
        assertTrue(game.isWithin(1, 1));
        assertFalse(game.isWithin(2, 2));
    }

}
