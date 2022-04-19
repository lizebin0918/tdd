package com.lzb.args;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
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
    private static String DIR = "-d";

    private Args() {
    }

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
     *
     * @param input
     * @return
     */
    public static Args parse(String input) {
        String[] inputs = input.split(" ");
        if (Objects.isNull(inputs) || inputs.length == 0) {
            return new Args(false, null, null);
        }

        boolean logging = logging(inputs);

        Integer port = port(inputs);

        String[] dirs = dirs(inputs);

        return new Args(logging, port, dirs);
    }

    private static String[] dirs(String[] inputs) {
        int dIndex = Arrays.binarySearch(inputs, DIR);
        if (dIndex >= 0) {
//            List<String> dirs = new ArrayList<>();
//            for (int i = dIndex + 1; i < inputs.length - dIndex; i++) {
//                dirs.add(inputs[i]);
//            }
            return Stream.of(inputs).skip(dIndex + 1L).toArray(String[]::new);
        }
        return new String[0];
    }

    private static Integer port(String[] inputs) {
        Integer port = null;
        int pIndex = Arrays.binarySearch(inputs, PORT);
        if (pIndex >= 0) {
            port = Integer.parseInt(inputs[pIndex + 1]);
        }
        return port;
    }

    /**
     * 日志
     * @param inputs
     * @return
     */
    private static boolean logging(String[] inputs) {
        return Arrays.asList(inputs).contains(LOGGING);
    }
    
    
}


