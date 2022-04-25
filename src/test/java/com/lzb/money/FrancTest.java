package com.lzb.money;

import org.junit.Test;

import static org.junit.Assert.*;

public class FrancTest {

    @Test
    public void test1() {
        Franc five = new Franc(5);
        assertEquals(new Franc(10), five.times(2));
        assertEquals(new Franc(15), five.times(3));
    }

}