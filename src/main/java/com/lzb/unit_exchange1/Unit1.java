package com.lzb.unit_exchange1;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-07-01 11:21
 * @author mac
 */
@Getter
public enum Unit1 {

    /**
     * 单位
     */
    Yard(36), Foot(12), Inch(1);

    private final int toInchRatio;

    Unit1(int toInchRatio) {
        this.toInchRatio = toInchRatio;
    }
}
