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

        Dollar money = new Dollar(5);
        Dollar ten = money.times(2);
        assertEquals(10, ten.getAmount());
        Dollar fifthteen = money.times(3);
        assertEquals(15, fifthteen.getAmount());

    }

    @Test
    public void test1() {
        Dollar five = new Dollar(5);
        assertEquals(new Dollar(10), five.times(2));
        assertEquals(new Dollar(15), five.times(3));
    }

}