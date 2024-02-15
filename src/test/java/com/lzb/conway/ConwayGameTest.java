package com.lzb.conway;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
    @DisplayName("test")
    void should_() {

    }
}
