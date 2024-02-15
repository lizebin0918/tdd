package com.lzb.bowling_game;

import java.util.Objects;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 帧<br/>
 * Created on : 2024-01-27 10:23
 *
 * @author lizebin
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class Frame extends BaseFrame {

    private final int index;

    /**
     * @param index 从1开始
     * @param balls
     */
    public Frame(int index, String balls) {
        super(balls);
        this.index = index;
    }

    public Frame(int index, Character first, Character second) {
        super(first, second);
        this.index = index;
    }

    public int getScore(Frame nextFrame, Frame nextNextFrame) {
        int currentFrameScore = getCurrentFrameScore();
        if (this.isSpare()) {
            return currentFrameScore + Optional.ofNullable(nextFrame).map(Frame::getFirst).map(Score::getScore).orElse(0);
        }
        if (this.isStrike()) {
            if (Objects.nonNull(nextFrame) && nextFrame.isStrike()) {
                return currentFrameScore
                    + nextFrame.getCurrentFrameScore()
                    + Optional.ofNullable(nextNextFrame).map(Frame::getFirst).map(Score::getScore).orElse(0);
            }
            return currentFrameScore + Optional.ofNullable(nextFrame).map(Frame::getCurrentFrameScore).orElse(0);
        }
        return currentFrameScore;
    }

    private int getCurrentFrameScore() {
        if (isSpare() || isStrike()) {
            return Score.TEN_SCORE;
        }
        int firstScore = Optional.ofNullable(first).map(Score::getScore).orElse(0);
        int secondScore = Optional.ofNullable(second).map(Score::getScore).orElse(0);
        return firstScore + secondScore;
    }

    public boolean isSpare() {
        return Optional.ofNullable(second).map(Score::isSpare).orElse(false);
    }

    public boolean isStrike() {
        return Optional.ofNullable(first).map(Score::isStrike).orElse(false);
    }

    boolean isSecondToLast(int size) {
        return getIndex() == size - 1;
    }

    boolean isLast(int size) {
        return getIndex() == size;
    }
}
