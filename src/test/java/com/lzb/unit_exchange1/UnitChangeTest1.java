package com.lzb.unit_exchange1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2023-07-01 09:15
 * @author mac
 */
class UnitChangeTest1 {

    @Test
    void test_inch_to_inch() {
        Length1 inch = new Length1(1, Unit1.Inch);
        Assertions.assertEquals(inch, new Length1(1, Unit1.Inch));
    }

    @Test
    void test_yard_to_yard() {
        Length1 yard = new Length1(1, Unit1.Yard);
        Assertions.assertEquals(yard, new Length1(1, Unit1.Yard));
    }

    @Test
    void test_foot_to_foot() {
        Length1 foot = new Length1(1, Unit1.Foot);
        Assertions.assertEquals(foot, new Length1(1, Unit1.Foot));
    }

    @Test
    void test_yard_to_inch() {
        Length1 yard = new Length1(1, Unit1.Yard);
        Assertions.assertEquals(yard, new Length1(36, Unit1.Inch));
    }

    @Test
    void test_foot_to_inch() {
        Length1 foot = new Length1(1, Unit1.Foot);
        Assertions.assertEquals(foot, new Length1(12, Unit1.Inch));
    }


    /*@Test
    void test_foot_to_inch() {
        Assertions.assertEquals(new Foot(1), new Length(12));
    }*/

}
