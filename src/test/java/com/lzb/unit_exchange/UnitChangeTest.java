package com.lzb.unit_exchange;

import com.lzb.AppTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 单位换算测试<br/>
 * Created on : 2023-06-29 23:08
 * @author mac
 */
class UnitChangeTest {

    ///////////////////////////////////////////////////////////////////////////
    // foot -> inch
    // foot -> yard
    // inch -> foot
    // inch -> yard
    // yard -> foot
    // yard -> inch
    // foot -> foot
    // inch -> inch
    // yard -> yard
    ///////////////////////////////////////////////////////////////////////////

    @Test
    void should_foot_to_inch() {
        UnitChange unitChange = new UnitChange(1, Unit.FOOT);
        Assertions.assertEquals(12, unitChange.toInch());
    }

    @Test
    void should_foot_to_yard() {
        UnitChange unitChange = new UnitChange(3, Unit.FOOT);
        Assertions.assertEquals(1, unitChange.toYard());
    }

    @Test
    void should_foot_to_foot() {
        UnitChange unitChange = new UnitChange(1, Unit.FOOT);
        Assertions.assertEquals(1, unitChange.toFoot());
    }

    @Test
    void should_inch_to_foot() {
        UnitChange unitChange = new UnitChange(12, Unit.INCH);
        Assertions.assertEquals(1, unitChange.toFoot());
    }

    @Test
    void should_inch_to_yard() {
        UnitChange unitChange = new UnitChange(36, Unit.INCH);
        Assertions.assertEquals(1, unitChange.toYard());
    }

    @Test
    void should_inch_to_inch() {
        UnitChange unitChange = new UnitChange(1, Unit.INCH);
        Assertions.assertEquals(1, unitChange.toInch());
    }

    @Test
    void should_yard_to_inch() {
        UnitChange unitChange = new UnitChange(1, Unit.YARD);
        Assertions.assertEquals(36,unitChange.toInch());
    }

    @Test
    void should_yard_to_foot() {
        UnitChange unitChange = new UnitChange(1, Unit.YARD);
        Assertions.assertEquals(3,unitChange.toFoot());
    }

    @Test
    void should_yard_to_yard() {
        UnitChange unitChange = new UnitChange(1, Unit.YARD);
        Assertions.assertEquals(1,unitChange.toYard());
    }

}
