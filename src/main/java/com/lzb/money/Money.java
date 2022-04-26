package com.lzb.money;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

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

    public static final String USD = "USD";
    public static final String CHF = "CHF";
    protected int amount;
    protected String currency;

    public static Money dollar(int amount) {
        return new Money(amount, USD);
    }

    public static Money franc(int amount) {
        return new Money(amount, CHF);
    }

    public Money times(int multiplier) {
        return new Money(amount * multiplier, currency);
    }

    public Money plus(Money money) {
        return plus1(money);
        /*if (USD.equals(currency)) {
            if (USD.equals(money.getCurrency())) {
                return Money.dollar(amount + money.getAmount());
            }
            return Money.dollar(amount + (money.getAmount() / 2));
        }

        if (CHF.equals(currency)) {
            if (USD.equals(money.getCurrency())) {
                return Money.franc(amount + money.getAmount() * 2);
            }
            return Money.franc(amount + money.getAmount());
        }

        return money;*/
    }

    public Money plus1(Money money) {

        // 同币种
        if (Objects.equals(currency, money.getCurrency())) {
            int newAmount = amount + money.getAmount();
            if (USD.equals(currency)) {
                return dollar(newAmount);
            }
            if (CHF.equals(currency)) {
                return franc(newAmount);
            }
        }

        if (USD.equals(currency)) {
            return Money.dollar(amount + (money.getAmount() / 2));
        }

        if (CHF.equals(currency)) {
            return Money.franc(amount + money.getAmount() * 2);
        }

        return money;
    }
}
