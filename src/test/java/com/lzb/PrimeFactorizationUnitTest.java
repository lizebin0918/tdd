package com.lzb;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <br/>
 * Created on : 2024-01-07 16:38
 * @author mac
 */
class PrimeFactorizationUnitTest extends BaseUnitTest {

    /*@Test
    @DisplayName("v1测试")
    void should_v1() {
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
    }*/

    @Test
    @DisplayName("v2合数分解质数 -> 1")
    void should_v2_input_1() {
        assertThat(PrimeFactorization.ofV2(1)).isEqualTo(List.of(1));
    }

    @Test
    @DisplayName("v2合数分解质数 -> 2")
    void should_v2_input_2() {
        assertThat(PrimeFactorization.ofV2(2)).isEqualTo(List.of(2));
    }

    @Test
    @DisplayName("v2合数分解质数 -> 3")
    void should_v2_input_3() {
        assertThat(PrimeFactorization.ofV2(3)).isEqualTo(List.of(3));
    }

    @Test
    @DisplayName("v2合数分解质数 -> 4")
    void should_v2_input_4() {
        assertThat(PrimeFactorization.ofV2(4)).isEqualTo(List.of(2, 2));
    }

    @Test
    @DisplayName("v2合数分解质数 -> 12")
    void should_v2_input_12() {
        assertThat(PrimeFactorization.ofV2(12)).isEqualTo(List.of(2, 2, 3));
    }

    @Test
    @DisplayName("v2合数分解质数 -> 49")
    void should_v2_input_49() {
        assertThat(PrimeFactorization.ofV2(49)).isEqualTo(List.of(7, 7));
    }

}
