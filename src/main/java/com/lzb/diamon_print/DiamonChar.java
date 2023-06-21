package com.lzb.diamon_print;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-06-19 22:39
 * @author mac
 */
@Getter
public class DiamonChar {

    private final char startChar;

    public static final char START_CHAR = 'A';

    public static final String DOT = ".";

    public DiamonChar(char startChar) {
        this.startChar = startChar;
    }

    List<String> diamonPrint() {
        int length = length();
        List<String> lines = new ArrayList<>(length);
        char tempStartChar = START_CHAR;
        int middle = (length / 2);
        for (int i = 0; i <= middle; i++) {
            lines.add(line(tempStartChar));
            tempStartChar++;
        }
        return reverseAppend(lines, 0, middle);
    }

    /**
     * 翻转append
     * @param lines
     * @param start
     * @param end
     * @return
     */
    List<String> reverseAppend(List<String> lines, int start, int end) {
        List<String> newLines = new ArrayList<>(lines);
        List<String> reverseSubLines = new ArrayList<>(newLines.subList(start, end));
        Collections.reverse(reverseSubLines);
        newLines.addAll(reverseSubLines);
        return newLines;
    }

    String line(char currentChar) {
        StringBuilder line = new StringBuilder();
        int middle = length() / 2;
        for (int i = -middle; i <= middle; i++) {
            if (Math.abs(i) == (currentChar - START_CHAR)) {
                line.append(currentChar);
                continue;
            }
            line.append(DOT);
        }
        return line.toString();
    }

    /**
     * 边长
     * @return
     */
    int length() {
        return (startChar - START_CHAR) * 2 + 1;
    }
}
