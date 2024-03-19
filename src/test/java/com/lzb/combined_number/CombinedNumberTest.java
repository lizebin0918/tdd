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

    @Test
    @DisplayName("找出最左边的数字最大的那个数，比如：[100, 2, 3]，返回索引 index")
    void should_return_2_when_input_100_2_3_start_index_0() {
        assertThat(CombinedNumber.maxNumOfLeftMost(new int[]{100, 2, 3}, 0, 0, 3)).isEqualTo(2);
    }

    @Test
    @DisplayName("找出最左边的数字最大的那个数，比如：[100, 2, 3, 4]，返回索引 index")
    void should_return_3_when_input_100_2_3_4_start_index_0() {
        assertThat(CombinedNumber.maxNumOfLeftMost(new int[]{100, 2, 3, 4}, 0, 0, 3)).isEqualTo(3);
    }

    @Test
    @DisplayName("找出最左边的数字最大的那个数，比如：[100, 2, 3, 4]，返回索引 3")
    void should_return_3_when_input_100_2_3_4_start_index_1() {
        assertThat(CombinedNumber.maxNumOfLeftMost(new int[]{100, 2, 3, 4}, 1, 0, 3)).isEqualTo(3);
    }

    @Test
    @DisplayName("swap")
    void should_swap() {
        int[] inputs = {100, 2, 3};
        CombinedNumber.swap(inputs, 0, 1);
        assertThat(inputs).isEqualTo(new int[]{2, 100, 3});
    }

    @Test
    @DisplayName("combine: 100, 2, 3 -> 10023")
    void should_return_10023_when_input_100_2_3() {
        assertThat(CombinedNumber.combine(new int[]{100, 2, 3})).isEqualTo("32100");
    }

    @Test
    @DisplayName("maxNumOfLeftMost([32, 33, 34], 0) -> 2")
    void should_return_2_when_maxNumOfLeftMost_32_33_34_0() {
        assertThat(CombinedNumber.maxNumOfLeftMost(new int[]{32, 33, 34}, 0, 1, 2)).isEqualTo(2);
    }



    @Nested
    class GetNumCharAt {
        @Test
        @DisplayName("getNumCharAt([32, 33, 345], 0, 2) -> 32, 33, 345")
        void should_return_2_when_getNumCharAt_32_33_345_0_2() {
            assertThat(CombinedNumber.getNumCharAt(new int[]{32, 33, 345}, 0, 2, 3)).isEqualTo(new int[]{2, 3, 5});
        }

        @Test
        @DisplayName("获取整个数组的最左边的数，100_2_3_4 返回数组 100_2_3_4")
        void should_return_array_of_left_most_when_input_100_2_3_4_start_index_1() {
            assertThat(CombinedNumber.getNumCharAt(new int[]{100, 2, 3, 4}, 1, 0. )).isEqualTo(new int[]{100, 2, 3, 4});
        }

        /*
        @Test
        @DisplayName("获取整个数组的最左边的数，100_2_3_4 返回数组 1_2_3_4")
        void should_return_array_of_left_most_when_input_100_2_3_4_start_index_0() {
            assertThat(CombinedNumber.getNumCharAt(new int[]{100, 2, 3, 4}, 0, 0)).isEqualTo(new int[]{1, 2, 3, 4});
        }*/

    }

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
