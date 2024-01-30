package com.lzb;

import java.util.ArrayList;
import java.util.List;

/**
 * 合数质因子分解<br/>
 * Created on : 2024-01-07 16:37
 * @author mac
 */
public class PrimeFactorization {

    static List<Integer> ofV2(int input) {
        List<Integer> result = new ArrayList<>();
        if (input == 1) {
            return List.of(1);
        }
        for (int i = 1, bound = input; i <= bound; i++) {
            while (i > 1 && (input % i) == 0) {
                result.add(i);
                input = input / i;
            }
        }
        return result;
    }

    public static List<Integer> ofV3(int input) {
        if (input == 1) {
            return List.of(1);
        }
        List<Integer> results = new ArrayList<>();
        for (int i = 2; i <= input; i++) {
            while (input % i == 0) {
                results.add(i);
                input = input / i;
            }
        }
        return results;
    }
}
