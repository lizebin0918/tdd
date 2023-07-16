package com.lzb.unit_test;

import java.nio.file.Path;
import java.util.List;

/**
 * 文件系统接口<br/>
 * Created on : 2023-07-15 23:18
 * @author lizebin
 */
public interface FileSystem {

    List<String> readAllLines(Path path);

    void createFile(Path path);

    void writeLine(Path path, String line);

    List<Path> readAllFiles(Path dir);
}
