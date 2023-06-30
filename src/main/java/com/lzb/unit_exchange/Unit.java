package com.lzb.unit_exchange;

/**
 * <br/>
 * Created on : 2023-06-29 23:24
 * @author mac
 */
public enum Unit {

    /**
     * 英尺、英寸、码
     */
    FOOT {
        @Override
        int toYard(int value) {
            return value / 3;
        }

        @Override
        int toInch(int value) {
            return value * 12;
        }

        @Override
        int toFoot(int value) {
            return value;
        }
    }, INCH {
        @Override
        int toYard(int value) {
            return value / 36;
        }

        @Override
        int toInch(int value) {
            return value;
        }

        @Override
        int toFoot(int value) {
            return value / 12;
        }
    }, YARD {
        @Override
        int toYard(int value) {
            return value;
        }

        @Override
        int toInch(int value) {
            return value * 36;
        }

        @Override
        int toFoot(int value) {
            return value * 3;
        }
    };

    abstract int toYard(int value);

    abstract int toInch(int value);
    abstract int toFoot(int value);

}
