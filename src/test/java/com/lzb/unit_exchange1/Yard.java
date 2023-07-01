package com.lzb.unit_exchange1;

import java.util.Objects;

public record Yard(int value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Yard yard)) return false;
        return value == yard.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
