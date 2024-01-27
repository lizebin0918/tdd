package com.lzb.bowling_game;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2024-01-24 10:13
 *
 * @author lizebin
 */
@Getter
public class BowlingGameCalculator {

    /*
     * X表示全中
     * /表示补中
     * -表示未击倒
     * |表示帧边界
     * ||之后的字符表示奖励球
     */
    public static final String STRIKE = "X";
    public static final String SPARE = "/";
    public static final String MISS = "-";
    public static final String FRAME_BOUNDARY = "|";
    public static final String BONUS_BOUNDARY = "||";

    private final Bonus bonus;

    /**
     *
     * @param input
     * @return
     */
    public BowlingGameCalculator(String input) {
        bonus = parseBonus(input);
    }

    private Bonus parseBonus(String input) {
        int index = input.lastIndexOf(BONUS_BOUNDARY);
        if (index < 0) {
            return Bonus.EMPTY;
        }
        return new Bonus(input.substring(index + BONUS_BOUNDARY.length(), input.length()));
    }

}
