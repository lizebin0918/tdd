package com.lzb.money;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2022-04-26 09:56
 *
 * @author lizebin
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Money {

    protected int amount;
    protected String currency;

    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money franc(int amount) {
        return new Money(amount, "CHF");
    }

    public Money times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

}
