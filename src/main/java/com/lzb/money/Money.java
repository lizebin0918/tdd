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
public abstract class Money {

    protected int amount;
    protected String currency;

    public static Dollar dollar(int amount) {
        return new Dollar(amount);
    }

    abstract Money times(int multiplier);

    /**
     * 币种
     * @return
     */
    abstract String currency();

}
