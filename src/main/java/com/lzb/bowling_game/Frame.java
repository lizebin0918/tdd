package com.lzb.bowling_game;

import java.util.Objects;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

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

    public int getScore(@Nullable Frame nextFrame) {
        if (Objects.isNull(nextFrame)) {
            return getCurrentFrameScore();
        }
        if (this.isSpare()) {
            return getCurrentFrameScore()
                + Optional.ofNullable(nextFrame.getFirst()).map(Score::getScore).orElse(0);
        }
        if (this.isStrike()) {
            return getCurrentFrameScore()
                + Optional.ofNullable(nextFrame.getFirst()).map(Score::getScore).orElse(0)
                + Optional.ofNullable(nextFrame.getSecond()).map(Score::getScore).orElse(0);
        }
        return getCurrentFrameScore();
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
}
