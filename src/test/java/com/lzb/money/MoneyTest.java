package com.lzb.money;

import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyTest {

    @Test
    public void testMultiplication() {

        ///////////////////////////////////////////////////////////////////////////
        // 产生副作用
        ///////////////////////////////////////////////////////////////////////////

        /*Dollar money = new Dollar(5);
        money.times(2);
        assertEquals(10, money.getAmount());
        money.times(3);
        assertEquals(15, money.getAmount());*/

        Money money = Money.dollar(5);
        Money ten = money.times(2);
        assertEquals(10, ten.getAmount());
        Money fifthteen = money.times(3);
        assertEquals(15, fifthteen.getAmount());

    }

    @Test
    public void test1() {
        Money five = Money.dollar(5);
        assertEquals(Money.dollar(10), five.times(2));
        assertEquals(Money.dollar(15), five.times(3));
    }

    @Test
    public void test_equals() {
        Money five = Money.dollar(5);
        Money five1 = five.times(1);
        assertEquals(five, five1);
        Money five2 = Money.dollar(5);
        assertEquals(five, five2);
    }

    /**
     * 5美元 + 5美元 = 10美元
     */
    @Test
    public void test_five_plus_five() {
        Money five = Money.dollar(5);
        Money five2 = Money.dollar(5);
        Money ten = Money.dollar(10);
        assertEquals(ten, five.plus(five2));
    }

    /**
     * 5美元+10瑞士法郎=10美元
     */
    @Test
    public void test_five_plus_ten_chf() {
        Money fiveDollar = Money.dollar(5);
        Money tenFranc = Money.franc(10);
        Money ten = Money.dollar(10);
        assertEquals(ten, fiveDollar.plus(tenFranc));
    }

    @Test
    public void test_five_plus_ten_chf_1() {
        Money fiveFranc = Money.franc(5);
        Money tenFranc = Money.franc(10);
        Money franc = Money.franc(15);
        assertEquals(franc, fiveFranc.plus(tenFranc));
    }

    @Test
    public void test_five_plus_ten_chf_2() {
        Money fiveFranc = Money.franc(5);
        Money tenDollar = Money.dollar(10);
        Money franc = Money.franc(25);
        assertEquals(franc, fiveFranc.plus(tenDollar));
    }

    @Test
    public void test_addition() {
        Money five = Money.dollar(5);
        Expression result = five.add(five);
        assertEquals(five, ((Sum)result).getAddend());
        assertEquals(five, ((Sum)result).getAddend());
    }

}