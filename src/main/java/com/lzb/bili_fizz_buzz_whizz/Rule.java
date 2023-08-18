package com.lzb.bili_fizz_buzz_whizz;

import java.util.Optional;

/**
 * 规则<br/>
 * Created on : 2023-08-17 21:41
 * @author mac
 */
@FunctionalInterface
public interface Rule {

    Optional<String> apply(int input);

}
