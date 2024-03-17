package com.lzb.sort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 冒泡排序<br/>
 * Created on : 2024-03-17 20:46
 *
 * @author lizebin
 */
class BubbleSortTest {

    @Test
    @DisplayName("input [1] output [1]")
    void should_ouput_1_when_input_1() {
        assertThat(BubbleSort.sort(new int[] {1})).isEqualTo(new int[] {1});
    }

}
