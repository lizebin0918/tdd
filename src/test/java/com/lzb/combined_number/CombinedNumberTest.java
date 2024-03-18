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
 * <br/>
 * Created on : 2024-03-17 20:58
 *
 * @author lizebin
 */
class CombinedNumberTest {


    @Disabled
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
                Arguments.of(new int[]{420, 42, 423}, "42423420")
        );
    }

}
