package com.lzb.bili_fizz_buzz_whizz;

/**
 * 为什么要用StringBuilder，因为后面可能要满足多个条件拼接字符串<br/>
 * Created on : 2023-08-17 18:01
 * @author mac
 */
public class RuleResult {

    private final StringBuilder result = new StringBuilder();

    public boolean append(boolean isMatch, String word) {
        if (isMatch) {
            result.append(word);
        }
        return isMatch;
    }

    public String getResult() {
        return result.toString();
    }

}
