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
@EqualsAndHashCode(callSuper = true)
public class Dollar extends Money {
    public Dollar(int amount) {
        super(amount);
    }

    public Dollar times(int multiplier) {
        return new Dollar(amount * multiplier);
    }
}
