package com.lzb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 合数质因子分解<br/>
 * Created on : 2024-01-07 16:37
 * @author mac
 */
public class PrimeFactorization {

    static List<Integer> ofV2(int input) {
        if (input == 1) {
            return Collections.emptyList();
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 2; i <= input; i++) {
            while (input % i == 0) {
                result.add(i);
                input /= i;
            }
        }
        return result;
    }


}
