package com.lzb.unit_exchange;

import java.util.Objects;

/**
 * <br/>
 * Created on : 2023-06-29 23:11
 * @author mac
 */
public record Length(int value, Unit unit) {


    /*public UnitChange(int value, Unit unit) {
        Objects.requireNonNull(unit);
        this.value = value;
        this.unit = unit;
    }*/

    public Length {
        Objects.requireNonNull(unit);
    }

    Length toInch() {
        return unit.toInch(value);
    }

    Length toYard() {
        return unit.toYard(value);
    }

    Length toFoot() {
        return unit.toFoot(value);
    }

}
