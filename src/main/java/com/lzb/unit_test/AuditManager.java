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
        Path filePath = getFilePath();
        String line = visitor + "; " + visitDateTime;
        fileSystem.writeLine(filePath, line);
        return filePath;
    }

    private Path getFilePath() {
        int current = Math.max(1, fileSystem.getFileCount());
        Path currentPath = getFilePath(current);
        int lineCount = fileSystem.readAllLines(currentPath).size();
        if (lineCount < maxPerFile) {
            return currentPath;
        }
        return getFilePath(current + 1);
    }

    private Path getFilePath(int current) {
        return Paths.get(dir + PREFIX + StringUtils.leftPad(Objects.toString(current), 2, '0') + SUFFIX);
    }

    List<String> listVisitors(Path filePath) {
        return fileSystem.readAllLines(filePath);
    }

}
