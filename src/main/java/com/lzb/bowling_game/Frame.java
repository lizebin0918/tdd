package com.lzb.bowling_game;

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
}
