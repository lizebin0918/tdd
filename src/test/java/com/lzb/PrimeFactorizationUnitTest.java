package com.lzb;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2024-01-07 16:38
 * @author mac
 */
class PrimeFactorizationUnitTest extends BaseUnitTest {

    @Test
    @DisplayName("测试")
    void should_() {
        assertThat(PrimeFactorization.of(2)).isEqualTo(List.of(2));
        assertThat(PrimeFactorization.of(4)).isEqualTo(List.of(2, 2));
        assertThat(PrimeFactorization.of(6)).isEqualTo(List.of(2, 3));
        assertThat(PrimeFactorization.of(12)).isEqualTo(List.of(2, 2, 3));
        assertThat(PrimeFactorization.of(7)).isEqualTo(List.of(7));
        assertThat(PrimeFactorization.of(24)).isEqualTo(List.of(2, 2, 2, 3));
        assertThat(PrimeFactorization.of(8)).isEqualTo(List.of(2, 2, 2));
        assertThat(PrimeFactorization.of(9)).isEqualTo(List.of(3, 3));
        assertThat(PrimeFactorization.of(25)).isEqualTo(List.of(5, 5));
        assertThat(PrimeFactorization.of(5)).isEqualTo(List.of(5));
        assertThat(PrimeFactorization.of(49)).isEqualTo(List.of(7, 7));
    }

}
