package com.lzb.unit_exchange;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 单位换算测试<br/>
 * Created on : 2023-06-29 23:08
 * @author mac
 */
class UnitChangeTest {

    @Test
    void should_foot_to_inch() {
        UnitChange unitChange = new UnitChange(1);
        Assertions.assertEquals(12, unitChange.toInch());
    }

    @Test
    void should_foot_to_yard() {
        UnitChange unitChange = new UnitChange(3);
        Assertions.assertEquals(1, unitChange.toYard());
    }

    @Test
    void should_foot_to_foot() {
        UnitChange unitChange = new UnitChange(1);
        Assertions.assertEquals(1, unitChange.toFoot());
    }

    @Test
    void should_inch_to_foot() {
        UnitChange unitChange = new UnitChange(12, Unit.INCH);
        Assertions.assertEquals(1, unitChange.toFoot());
    }

}
