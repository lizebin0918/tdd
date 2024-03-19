package com.lzb.combined_number;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <br/>
 * Created on : 2024-03-17 20:57
 *
 * @author lizebin
 */
public class CombinedNumber {

    public static int maxNumOfLeftMost(int[] inputs, int start, int charAt, int maxLength) {
        if (Objects.isNull(inputs) || inputs.length == 0) {
            return -1;
        }
        if (start == inputs.length - 1) {
            return start;
        }
        int[] numOfLeftMost = getNumCharAt(inputs, start, charAt, maxLength);
        int maxIndex = start;
        for (int i = maxIndex + 1; i < numOfLeftMost.length; i++) {
            if (numOfLeftMost[maxIndex] < numOfLeftMost[i]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    protected static void swap(int[] inputs, int i, int j) {
        int temp = inputs[i];
        inputs[i] = inputs[j];
        inputs[j] = temp;
    }


    protected static int[] getNumCharAt(int[] inputs, int start, int charAt) {
        int[] numOfLeftMost = new int[inputs.length];
        for (int i = 0; i < numOfLeftMost.length; i++) {
            if (i >= start) {
                String numString = Objects.toString(inputs[i]);
                if (charAt <= numString.length() - 1) {
                    numOfLeftMost[i] = Integer.valueOf(numString.substring(charAt, charAt + 1));
                } else {
                    numOfLeftMost[i] = 0;
                }
            } else {
                numOfLeftMost[i] = inputs[i];
            }
        }
        return numOfLeftMost;
    }

    public static String combine(int[] inputs) {
        int maxLength = IntStream.of(inputs).boxed().map(Objects::toString).max(Comparator.comparing(CharSequence::length)).map(String::length).orElse(0);
        for (int j = 0; j < maxLength; j++) {
            for (int i = 0; i < inputs.length; i++) {
                int maxIndex = maxNumOfLeftMost(inputs, i, j, j);
                if (maxIndex != i) {
                    swap(inputs, i, maxIndex);
                }
            }
        }
        return IntStream.of(inputs).boxed().map(Objects::toString).collect(Collectors.joining());
    }
}
