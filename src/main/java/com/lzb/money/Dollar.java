package com.lzb.money;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2022-04-25 22:20
 *
 * @author lizebin
 */
@Getter
@EqualsAndHashCode
public class Dollar {

    private final int amount;

    public Dollar(int amount) {
        this.amount = amount;
    }

    public Dollar times(int multiplier) {
        return new Dollar(amount * multiplier);
    }
}
