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
@EqualsAndHashCode
@AllArgsConstructor
public class Franc {

    private int amount;

    public Franc times(int multiplier) {
        return new Franc(amount * multiplier);
    }

}
