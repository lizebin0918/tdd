package com.lzb;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 合数质因子分解<br/>
 * Created on : 2024-01-07 16:37
 * @author mac
 */
public class PrimeFactorization {

    static List<Integer> of(int input) {
        List<Integer> result = new ArrayList<>();
        List<Integer> primes = IntStream.iterate(2, i -> i + 1).limit(input).boxed().toList();
        int index = 0;
        while(index < input) {
            int prime = primes.get(index);
            if (input % prime == 0) {
                result.add(prime);
                input /= prime;
                index = 0;
            } else {
                index++;
            }
        }
        return result;
    }
}
