package com.lzb.bowling_game;

import java.util.ArrayList;
import java.util.List;
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
    public static final char STRIKE = 'X';
    public static final char SPARE = '/';
    public static final char MISS = '-';
    public static final String FRAME_BOUNDARY = "|";
    public static final String BONUS_BOUNDARY = "||";
    public static final String ESCAPE_CHARACTER_FRAME_BOUNDARY = "\\|";

    private Bonus bonus;
    private List<Frame> frames = new ArrayList<>();

    /**
     *
     * @param input
     * @return
     */
    public BowlingGameCalculator(String input) {
        initBonus(input);
        initFrames(input);
    }

    private void initFrames(String input) {
        String[] frameStrings = parseFrameString(input);
        for (int i = 0; i < frameStrings.length; i++) {
            frames.add(new Frame(i + 1, frameStrings[i]));
        }
    }

    protected static String[] parseFrameString(String input) {
        int bonusIndex = input.lastIndexOf(BONUS_BOUNDARY);
        int end = bonusIndex < 0 ? input.length() : bonusIndex;
        return input.substring(0, end).split(ESCAPE_CHARACTER_FRAME_BOUNDARY);
    }

    private void initBonus(String input) {
        int index = input.lastIndexOf(BONUS_BOUNDARY);
        if (index < 0) {
            bonus = Bonus.EMPTY;
            return;
        }
        bonus = new Bonus(input.substring(index + BONUS_BOUNDARY.length(), input.length()));
    }

}
