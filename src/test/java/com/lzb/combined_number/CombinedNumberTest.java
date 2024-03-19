package com.lzb.combined_number;

import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Write a function accepting a list of non negative integers, and returning their largest possible combined number as a string. For example
 *
 * given [50, 2, 1, 9]  it returns "95021"    (9 + 50 + 2 + 1)
 * given [5, 50, 56]    it returns "56550"    (56 + 5 + 50)
 * given [420, 42, 423] it returns "42423420" (42 + 423 + 420)
 *
 * Source [https://blog.svpino.com/about]
 * <br/>
 * Created on : 2024-03-17 20:58
 *
 * @author lizebin
 */
class CombinedNumberTest {

    @Test
    @DisplayName("isGreater: 50 > 2")
    void should_true_when_input_50_2() {
        assertThat(CombinedNumber.isGreater(50,2)).isTrue();
    }

    @Test
    @DisplayName("isGreater: 2 < 50")
    void should_true_when_input_2_50() {
        assertThat(CombinedNumber.isGreater(2,50)).isFalse();
    }

    @Test
    @DisplayName("isGreater: 56 > 5")
    void should_true_when_input_56_6() {
        assertThat(CombinedNumber.isGreater(56,5)).isTrue();
    }

    @Test
    @DisplayName("isGreater: 8 < 9")
    void should_true_when_input_8_9() {
        assertThat(CombinedNumber.isGreater(8,9)).isFalse();
    }

    @Test
    @DisplayName("combine: [1, 2]")
    void should_combine_1_2() {
        assertThat(CombinedNumber.combine(new int[]{1, 2})).isEqualTo("21");
    }

    @Test
    @DisplayName("combine: [1, 2, 3]")
    void should_combine_1_2_3() {
        assertThat(CombinedNumber.combine(new int[]{1, 2, 3})).isEqualTo("321");
    }

    @DisplayName("验收测试")
    @ParameterizedTest
    @MethodSource("acceptTest")
    void should_pass_accept_test(int[] inputs, String expected) {
        assertThat(CombinedNumber.combine(inputs)).isEqualTo(expected);
    }

    static Stream<Arguments> acceptTest() {
        return Stream.of(
                Arguments.of(new int[]{50, 2, 1, 9}, "95021"),
                Arguments.of(new int[]{5, 50, 56}, "56550"),
                Arguments.of(new int[]{5, 50, 65}, "65550"),
                Arguments.of(new int[]{54, 50, 56}, "565450"),
                Arguments.of(new int[]{420, 42, 423}, "42423420")
        );
    }

}
