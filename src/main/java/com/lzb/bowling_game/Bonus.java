package com.lzb.bowling_game;

import java.util.Objects;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

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
}
