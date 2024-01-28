package com.lzb.bowling_game;

import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 帧<br/>
 * Created on : 2024-01-27 10:08
 *
 * @author lizebin
 */
@Getter
@EqualsAndHashCode
public abstract class BaseFrame {

    protected Score first;
    protected Score second;

    protected BaseFrame(Character first, Character second) {
        this.first = Score.of(first).orElse(null);
        this.second = Score.of(second).orElse(null);
    }

    protected BaseFrame(String balls) {
        if (Objects.isNull(balls) || StringUtils.isBlank(balls)) {
            return;
        }
        this.first = Score.of(balls.charAt(0)).orElse(null);
        if (balls.length() > 1) {
            this.second = Score.of(balls.charAt(1)).orElse(null);
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (Objects.nonNull(first)) {
            result.append(first.getSymbol());
        }
        if (Objects.nonNull(second)) {
            result.append(second.getSymbol());
        }
        return result.toString();
    }

}
