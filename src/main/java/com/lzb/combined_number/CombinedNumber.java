package com.lzb.combined_number;

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

    public static boolean isGreater(int a, int b) {
        String ab = a + "" + b;
        String ba = b + "" + a;
        return Integer.parseInt(ab) > Integer.parseInt(ba);
    }

    protected static void swap(int[] inputs, int i, int j) {
        int temp = inputs[i];
        inputs[i] = inputs[j];
        inputs[j] = temp;
    }

    public static String combine(int[] inputs) {
        for (int maxIndex = 0; maxIndex < inputs.length; maxIndex++) {
            for (int currentIndex = maxIndex + 1; currentIndex < inputs.length; currentIndex++) {
                if (isGreater(inputs[currentIndex], inputs[maxIndex])) {
                    swap(inputs, currentIndex, maxIndex);
                }
            }
        }
        return IntStream.of(inputs).mapToObj(Objects::toString).collect(Collectors.joining());
    }
}
