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
    @DisplayName("初始化活细胞")
    void should_init_live() {
        ConwayGame game = new ConwayGame(4, 2);
        game.live(0, 0);
        assertArrayEquals(new int[][]{
                {1, 0, 0, 0},
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

        List<Point> livePoints = game.pointsOfDeadToLive();
        assertEquals(2, livePoints.size());
        assertTrue(livePoints.containsAll(List.of(new Point(1, 0), new Point(1, 1))));
    }

    @Test
    @DisplayName("定位死细胞")
    void should_return_value_when_dead() {
        ConwayGame game = new ConwayGame(new int[][] {
                {1, 0, 1, 0},
                {1, 0, 0, 0}
        });

        assertFalse(game.isDead(0, 0));
        assertTrue(game.isDead(1, 0));
        assertFalse(game.isDead(2, 0));
        assertTrue(game.isDead(3, 0));
    }

    @Test
    @DisplayName("遍历获取活细胞数量")
    void should_return_live_count() {
        ConwayGame game = new ConwayGame(4, 2);
        game.live(0, 0).live(0, 1).live(2, 0);

        /**
         {1, 0, 1, 0},
         {1, 0, 0, 0}
         **/

        assertEquals(3, game.traversalLive(1, 0));
    }

    @Test
    @DisplayName("如果图形是长方形，获取编号2的细胞状态")
    void should_return_get_two_when_gird_is_rectangle() {
        ConwayGame game = new ConwayGame(new int[][] {
                {1, 0, 1, 0},
                {1, 0, 0, 0}
        });

        assertEquals(1, game.getTwo(1, 0));
    }

    @Test
    @DisplayName("获取序号1的值")
    void should_return_index1_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(new int[][] {
                {1, 0, 0, 0},
                {0, 0, 0, 0}
        });
        assertEquals(1, game.getOne(1, 1));
    }

    @Test
    @DisplayName("获取序号2的值")
    void should_return_index2_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(3, 3);
        game.live(0, 1);
        assertEquals(1, game.getTwo(1, 1));
    }

    @Test
    @DisplayName("获取序号3的值")
    void should_return_index3_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(3, 3);
        game.live(0, 2);
        assertEquals(1, game.getThree(1, 1));
    }

    @Test
    @DisplayName("获取序号4的值")
    void should_return_index4_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(3, 3);
        game.live(1, 2);
        assertEquals(1, game.getFour(1, 1));
    }

    @Test
    @DisplayName("获取序号5的值")
    void should_return_index5_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(3, 3);
        game.live(2, 2);
        assertEquals(1, game.getFive(1, 1));
    }

    @Test
    @DisplayName("获取序号6的值")
    void should_return_index6_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(3, 3);
        game.live(2, 1);
        assertEquals(1, game.getSix(1, 1));
    }

    @Test
    @DisplayName("获取序号7的值")
    void should_return_index7_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(3, 3);
        game.live(2, 0);
        assertEquals(1, game.getSeven(1, 1));
    }

    @Test
    @DisplayName("获取序号8的值")
    void should_return_index8_value_when_location_is_x_1_y_1() {
        ConwayGame game = new ConwayGame(3, 3);
        game.live(1, 0);
        assertEquals(1, game.getEight(1, 1));
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

        List<Point> livePoints = game.pointsOfLiveToDead();
        assertEquals(3, livePoints.size());
        assertTrue(livePoints.containsAll(List.of(new Point(0, 0), new Point(0, 1), new Point(2, 0))));
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

        List<Point> livePoints = game.pointsOfLiveToDead();
        assertEquals(2, livePoints.size());
        assertTrue(livePoints.containsAll(List.of(new Point(1, 1), new Point(1, 0))));
    }

    @Test
    @DisplayName("测试2*2网格，活细胞灭亡")
    void should_live_to_dead_when_2_2_gird() {
        ConwayGame game = new ConwayGame(2, 2);
        game.live(0, 0).live(1, 1);
        /*
        {1, 0},
        {0, 1}
         */
        game.tick();
        assertArrayEquals(new int[][]{
                {0, 0},
                {0, 0}
        }, game.getGrid());
    }

    @Test
    @DisplayName("测试2*2网格，死细胞存活")
    void should_dead_to_live_when_2_2_gird() {
        ConwayGame game = new ConwayGame(2, 2);
        game.live(0, 0).live(0, 1).live(1, 0);
        /*
        {1, 1},
        {1, 0}
         */
        game.tick();
        assertArrayEquals(new int[][]{
                {1, 1},
                {1, 1}
        }, game.getGrid());
    }

    @Test
    @DisplayName("测试2*2网格，无细胞变化")
    void should_no_change_when_2_2_gird() {
        ConwayGame game = new ConwayGame(2, 2);
        game.live(0, 0).live(0, 1).live(1, 0).live(1, 1);
        /*
        {1, 1},
        {1, 1}
         */
        game.tick();
        assertArrayEquals(new int[][]{
                {1, 1},
                {1, 1}
        }, game.getGrid());
    }


}
