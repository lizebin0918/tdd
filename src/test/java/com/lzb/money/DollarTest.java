package com.lzb.money;

import org.junit.Test;

import static org.junit.Assert.*;

public class DollarTest {

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
        Franc franc = new Franc(5);
    }

}