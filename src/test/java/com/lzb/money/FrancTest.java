package com.lzb.money;

import org.junit.Test;

import static org.junit.Assert.*;

public class FrancTest {

    @Test
    public void test1() {
        Franc five = Money.franc(5);
        assertEquals(Money.franc(10), five.times(2));
        assertEquals(Money.franc(15), five.times(3));
    }

}