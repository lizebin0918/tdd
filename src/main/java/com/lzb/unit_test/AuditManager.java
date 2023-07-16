package com.lzb.unit_test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * <br/>
 * Created on : 2023-07-15 23:02
 * @author lizebin
 */
@Getter
public class AuditManager {

    public static final String PREFIX = "audit_";
    public static final String SUFFIX = ".txt";

    private final int maxPerFile;
    private final String dir;
    private final FileSystem fileSystem;

    private AuditManager(String dir, int maxPerFile, FileSystem fileSystem) {
        this.dir = dir;
        this.maxPerFile = maxPerFile;
        this.fileSystem = fileSystem;
    }

    public AuditManager(FileSystem fileSystem) {
        this("/Users/lizebin/Desktop/", 3, fileSystem);
    }

    Path visit(String visitor, LocalDateTime visitDateTime) {
        int fileSize = getFileSize();
        Path lastFilePath = Paths.get(dir + PREFIX + StringUtils.leftPad(Objects.toString(fileSize), 2, '0') + SUFFIX);
        int size = listVisitors(lastFilePath).size();
        if (size == maxPerFile) {
            lastFilePath = Paths.get(dir + PREFIX + StringUtils.leftPad(Objects.toString(fileSize + 1), 2, '0') + SUFFIX);
            fileSystem.createFile(lastFilePath);
        }
        fileSystem.writeLine(lastFilePath, visitor + "; " + visitDateTime);
        System.out.println(fileSystem);
        return lastFilePath;
    }

    List<String> listVisitors(Path filePath) {
        return fileSystem.readAllLines(filePath);
    }

    int getFileSize() {
        return fileSystem.readAllFiles(Paths.get(dir)).size();
    }
}
