package com.lzb.tennis;

import java.util.Objects;

/**
 * <br/>
 * Created on : 2024-01-12 10:22
 * @author lizebin
 */
public class Score {

    public static Score love() {
        return new Score(0);
    }

    public static Score fifteen() {
        return new Score(1);
    }

    public static Score thirty() {
        return new Score(2);
    }

    public static Score forty() {
        return new Score(3);
    }

    private int value;

    public Score(int value) {
        this.value = value;
    }

    boolean isGreaterForty() {
        return getValue() >= 3;
    }

    public int increment() {
        value += 1;
        return value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Score score)) return false;
        return value == score.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
