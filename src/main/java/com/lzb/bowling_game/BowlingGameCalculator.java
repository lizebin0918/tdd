package com.lzb.bowling_game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    /**
     * 获取帧的分数
     *
     * @param frameIndex 从1开始
     * @return
     */
    public int getFrameScore(int frameIndex) {
        Frame frame = frames.get(frameIndex - 1);
        Frame nextFrame = isInBoundary(frameIndex) ? frames.get(frameIndex) : null;
        Frame nextNextFrame = isInBoundary(frameIndex + 1) ? frames.get(frameIndex + 1) : null;

        // 倒数第二帧
        if (frameIndex == frames.size() - 1 && isStrike(frameIndex)) {
            return frame.getScore(nextFrame, null)
                + (Optional.ofNullable(nextFrame).map(Frame::isStrike).orElse(false) ? bonus.getFirst().getScore() : 0);
        }

        // 最后一帧
        if (frameIndex == frames.size() && (isSpare(frameIndex) || isStrike(frameIndex))) {
            return frame.getScore(null, null) + getBonusScore();
        }

        return frame.getScore(nextFrame, nextNextFrame);
    }

    private boolean isInBoundary(int frameIndex) {
        return frameIndex <= frames.size() - 1;
    }

    public boolean isSpare(int frameIndex) {
        int actualFrameIndex = frameIndex - 1;
        if (!isInBoundary(actualFrameIndex)) {
            return false;
        }
        return frames.get(actualFrameIndex).isSpare();
    }

    public boolean isStrike(int frameIndex) {
        int actualFrameIndex = frameIndex - 1;
        if (!isInBoundary(actualFrameIndex)) {
            return false;
        }
        return frames.get(actualFrameIndex).isStrike();
    }

    public int getBonusScore() {
        return bonus.getScore();
    }

    public int getTotalScore() {
        int total = 0;
        for (int i = 1; i <= frames.size(); i++) {
            total += getFrameScore(i);
        }
        return total;
    }
}
