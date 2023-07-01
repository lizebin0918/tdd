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
        Length toYard(int value) {
            return new Length(value / 3, YARD);
        }

        @Override
        Length toInch(int value) {
            return new Length(value * 12, INCH);
        }

        @Override
        Length toFoot(int value) {
            return new Length(value, FOOT);
        }
    },
    INCH {
        @Override
        Length toYard(int value) {
            return new Length(value / 36, YARD);
        }

        @Override
        Length toInch(int value) {
            return new Length(value, INCH);
        }

        @Override
        Length toFoot(int value) {
            return new Length(value / 12, FOOT);
        }
    },
    YARD {
        @Override
        Length toYard(int value) {
            return new Length(value, YARD);
        }

        @Override
        Length toInch(int value) {
            return new Length(value * 36, INCH);
        }

        @Override
        Length toFoot(int value) {
            return new Length(value * 3, FOOT);
        }
    };

    abstract Length toYard(int value);

    abstract Length toInch(int value);

    abstract Length toFoot(int value);

}
