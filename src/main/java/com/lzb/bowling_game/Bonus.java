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
public class Bonus {

    private Character first;
    private Character second;

    public static final Bonus EMPTY = new Bonus(null, null);
    private Bonus(Character first, Character second) {
        this.first = first;
        this.second = second;
    }

    public Bonus(String bonus) {
        if (Objects.isNull(bonus) || StringUtils.isBlank(bonus)) {
            return;
        }
        this.first = bonus.charAt(0);
        this.second = bonus.charAt(1);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (Objects.nonNull(first)) {
            result.append(first);
        }
        if (Objects.nonNull(second)) {
            result.append(second);
        }
        return result.toString();
    }
}
