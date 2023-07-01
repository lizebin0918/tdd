package com.lzb.unit_exchange1;

import java.util.Objects;

/**
 * <br/>
 * Created on : 2023-07-01 09:17
 * @author mac
 */
public record Length1(int value, Unit1 unit) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Length1 length)) return false;
        return value == length.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
