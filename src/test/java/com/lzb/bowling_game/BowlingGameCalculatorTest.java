package com.lzb.bowling_game;

import com.lzb.BaseUnitTest;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 保龄球游戏<br/>
 * 编写一个程序来计算保龄球比赛的得分。
 *
 * 输入：表示保龄球比赛的字符串（如下所述）
 * 输出：整数得分
 *
 * 计分规则：
 *
 * 每场比赛或称为保龄球线包括十轮或称为帧的投球。
 *
 * 在每一帧中，投球者有两次机会击倒全部十个瓶子。
 *
 * 如果一帧中的第一球击倒全部十个瓶子，这被称为“全中”。该帧结束。该帧的得分是十加上接下来两球击倒的瓶子的总数。
 *
 * 如果一帧中的第二球击倒全部十个瓶子，这被称为“补中”。该帧结束。该帧的得分是十加上接下来一球击倒的瓶子的数量。
 *
 * 如果在两球之后，仍然有至少一个瓶子站着，那么该帧的得分就是这两球击倒的瓶子的总数。
 *
 * 如果在最后（第十）帧中获得了补中，你将获得一个额外的奖励球。
 *
 * 如果在最后（第十）帧中获得了全中，你将获得两个额外的奖励球。
 *
 * 这些奖励球被视为同一轮的一部分。如果奖励球击倒了所有的瓶子，该过程不会重复。奖励球仅用于计算最后一帧的得分。
 *
 * 比赛得分是所有帧得分的总和。
 *
 * 示例：
 *
 * X表示全中
 * /表示补中
 * -表示未击倒
 * |表示帧边界
 * ||之后的字符表示奖励球
 *
 * X|X|X|X|X|X|X|X|X|X||XX
 * 前十个帧的第一球都是全中。
 * 两个奖励球，都是全中。
 * 每帧的得分== 10 + 下两球的得分 == 10 + 10 + 10 == 30
 * 总得分== 10帧 x 30 == 300
 *
 * 9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||
 * 前十个帧的第一球都击中了九个瓶子。
 * 每帧的第二球都未击倒最后一个瓶子。
 * 没有奖励球。
 * 每帧的得分== 9
 * 总得分== 10帧 x 9 == 90
 *
 * 5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5
 * 前十个帧的第一球都击中了五个瓶子。
 * 每帧的第二球都击中了剩下的五个瓶子，补中。
 * 一个奖励球，击中了五个瓶子。
 * 每帧的得分== 10 + 下一球的得分 == 10 + 5 == 15
 * 总得分== 10帧 x 15 == 150
 *
 * X|7/|9-|X|-8|8/|-6|X|X|X||81
 * 总得分== 167
 * Created on : 2024-01-24 10:12
 *
 *
 * @author lizebin
 */
class BowlingGameCalculatorTest extends BaseUnitTest {


    /**
     * 解析字符串，分别是奖励球和帧
     * 按帧解析对应字符，算出每帧得分
     */

    @Test
    @DisplayName("解析字符串：截取奖励球 ")
    void should_parse_input_string_for_bonus() {
        BowlingGameCalculator bowlingGameCalculator = new BowlingGameCalculator("X|X|X|X|X|X|X|X|X|X||XX");
        Assertions.assertEquals("XX", bowlingGameCalculator.getBonus().toString());
    }

    @Test
    @DisplayName("解析字符串：截取奖励球,没有奖励球")
    void should_parse_input_string_for_bonus_when_bonus_is_empty() {
        BowlingGameCalculator bowlingGameCalculator = new BowlingGameCalculator("9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||");
        Assertions.assertEquals("", bowlingGameCalculator.getBonus().toString());
    }

    @Test
    @DisplayName("解析字符串：截取奖励球,有一个奖励球")
    void should_parse_input_string_for_bonus_when_bonus_has_one() {
        BowlingGameCalculator bowlingGameCalculator = new BowlingGameCalculator("9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||X");
        Assertions.assertEquals("X", bowlingGameCalculator.getBonus().toString());
    }

    @Test
    @DisplayName("解析字符串：比赛中途，还没打完")
    void should_parse_input_string_for_game_is_not_end() {
        BowlingGameCalculator bowlingGameCalculator = new BowlingGameCalculator("9-|9-|9-|9-|9-|9-|9-|9-|9-|");
        Assertions.assertEquals("", bowlingGameCalculator.getBonus().toString());
    }

    @Test
    @DisplayName("解析字符串：截取帧")
    void should_parse_input_string_for_frame() {
        BowlingGameCalculator bowlingGameCalculator = new BowlingGameCalculator("X|X");
        Assertions.assertEquals(List.of(new Frame(1, "X"), new Frame(2, "X")),
            bowlingGameCalculator.getFrames());
    }

}
