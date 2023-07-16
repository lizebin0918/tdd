package com.lzb.unit_test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        Path filePath = Paths.get(dir + PREFIX + StringUtils.leftPad(Objects.toString(Math.max(1, fileSystem.getFileCount())), 2, '0') + SUFFIX);
        int lineCount = fileSystem.readAllLines(filePath).size();
        if (lineCount >= maxPerFile) {
            filePath = Paths.get(dir + PREFIX + StringUtils.leftPad(Objects.toString(fileSystem.getFileCount() + 1), 2, '0') + SUFFIX);
        }
        String line = visitor + "; " + visitDateTime;
        fileSystem.writeLine(filePath, line);
        return filePath;
    }

    List<String> listVisitors(Path filePath) {
        return fileSystem.readAllLines(filePath);
    }

}
