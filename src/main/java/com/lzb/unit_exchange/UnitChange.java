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
        if (unit == Unit.YARD) {
            return value * 36;
        }
        if (unit == Unit.INCH) {
            return value;
        }
        if (unit == Unit.FOOT) {
            return value * 12;
        }
        return 0;
    }

    int toYard() {
        if (unit == Unit.INCH) {
            return value / 36;
        }
        if (unit == Unit.YARD) {
            return value;
        }
        if (unit == Unit.FOOT) {
            return value / 3;
        }
        return 0;
    }

    int toFoot() {
        if (unit == Unit.INCH) {
            return value / 12;
        }
        if (unit == Unit.YARD) {
            return value * 3;
        }
        if (unit == Unit.FOOT) {
            return value;
        }
        return 0;
    }
}
