package com.lzb.unit_exchange;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-06-29 23:11
 * @author mac
 */
@Getter
public class UnitChange {

    private final int value;

    private final Unit unit;

    public UnitChange(int value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    int toInch() {
        return value * 12;
    }

    int toYard() {
        return value / 3;
    }

    int toFoot() {
        if (unit == Unit.INCH) {
            return value / 12;
        }
        return value;
    }
}
