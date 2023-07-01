package com.lzb.unit_exchange1;

import java.util.Objects;

public record Foot(int value) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Foot foot)) return false;
        return value == foot.value;
    }
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
