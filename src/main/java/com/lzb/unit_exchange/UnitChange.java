package com.lzb.unit_exchange;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-06-29 23:11
 * @author mac
 */
public record UnitChange(int value, Unit unit) {

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
        throw new IllegalArgumentException("无法转换");
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
        throw new IllegalArgumentException("无法转换");
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
        throw new IllegalArgumentException("无法转换");
    }
}
