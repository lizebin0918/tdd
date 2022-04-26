package com.lzb.money;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 法郎<br/>
 * Created on : 2022-04-25 23:39
 *
 * @author lizebin
 */
@Getter
@EqualsAndHashCode(callSuper = true)
public class Franc extends Money {

    public Franc(int amount) {
        super(amount, "CHF");
    }

    Franc times(int multiplier) {
        return new Franc(amount * multiplier);
    }

    @Override
    public String currency() {
        return currency;
    }

}
