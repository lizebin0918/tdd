package com.lzb.unit_exchange;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-06-29 23:11
 * @author mac
 */
@Getter
public class UnitChange {

    private final int foot;

    private final Unit unit;

    public UnitChange(int foot) {
        this.foot = foot;
        this.unit = Unit.FOOT;
    }

    public UnitChange(int foot, Unit unit) {
        this.foot = foot;
        this.unit = unit;
    }

    int toInch() {
        return foot * 12;
    }

    int toYard() {
        return foot / 3;
    }

    int toFoot() {
        if (unit == Unit.INCH) {
            return foot / 12;
        }
        return foot;
    }
}
