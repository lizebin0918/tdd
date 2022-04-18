package com.lzb.args;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 参数:-l -p 8080 -d /usr/logs <br/>
 * Created on : 2022-04-18 19:34
 *
 * @author lizebin
 */
@Getter
@AllArgsConstructor
public class Args {

    private static final String LOGGING = "-l";
    private static final String PORT = "-p";

    private Args() {}

    /**
     * 是否记录日志
     */
    private boolean logging;
    /**
     * 端口号
     */
    private Integer port;
    /**
     * 文件目录
     */
    private String[] dirs;

    /**
     * 解析命令，按照空格分开
     * @param input
     * @return
     */
    public static Args parse(String input) {
        String[] inputs = input.split(" ");

        boolean logging = Arrays.asList(inputs).contains(LOGGING);

        Integer port = null;
        int pIndex = Arrays.binarySearch(inputs, PORT);
        if (pIndex >= 0) {
            port = Integer.parseInt(inputs[pIndex + 1]);
        }

        return new Args(logging, port, null);
    }
}
