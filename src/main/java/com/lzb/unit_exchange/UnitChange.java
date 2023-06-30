package com.lzb.unit_exchange;

import java.util.Objects;

/**
 * <br/>
 * Created on : 2023-06-29 23:11
 * @author mac
 */
public record UnitChange(int value, Unit unit) {


    /*public UnitChange(int value, Unit unit) {
        Objects.requireNonNull(unit);
        this.value = value;
        this.unit = unit;
    }*/

    public UnitChange {
        Objects.requireNonNull(unit);
    }

    int toInch() {
        return unit.toInch(value);
    }

    int toYard() {
        return unit.toYard(value);
    }

    int toFoot() {
        return unit.toFoot(value);
    }
}
