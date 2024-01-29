package com.lzb.bowling_game;

import java.util.Optional;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2024-01-26 13:48
 *
 * @author lizebin
 */
@Getter
public class Bonus extends BaseFrame {

    public static final Bonus EMPTY = new Bonus(null, null);

    private Bonus(Character first, Character second) {
        super(first, second);
    }

    public Bonus(String ball) {
        super(ball);
    }

    public int getScore() {
        return Optional.ofNullable(first).map(Score::getScore).orElse(0)
            + Optional.ofNullable(second).map(Score::getScore).orElse(0);
    }
}
