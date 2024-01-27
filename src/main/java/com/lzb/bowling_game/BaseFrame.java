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


    protected Character first;
    protected Character second;

    protected BaseFrame(Character first, Character second) {
        this.first = first;
        this.second = second;
    }

    protected BaseFrame(String balls) {
        if (Objects.isNull(balls) || StringUtils.isBlank(balls)) {
            return;
        }
        this.first = balls.charAt(0);
        if (balls.length() > 1) {
            this.second = balls.charAt(1);
        }
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
