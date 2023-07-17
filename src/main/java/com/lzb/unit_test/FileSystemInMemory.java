package com.lzb.unit_test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson.JSON;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-07-16 11:07
 * @author lizebin
 */
public class FileSystemInMemory implements FileSystem {

    private final List<FileInMemory> files = new ArrayList<>();

    @Override
    public List<String> readAllLines(Path filePath) {
        for (FileInMemory file : files) {
            if (Objects.equals(file.getName(), filePath.getFileName().toString())) {
                return file.getLines();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void applyUpdateFile(UpdateFile updateFile) {
        Path filePath = updateFile.filePath();
        String line = updateFile.line();
        for (FileInMemory file : files) {
            if (Objects.equals(file.getName(), filePath.getFileName().toString())) {
                file.addLine(line);
                return;
            }
        }
        FileInMemory file = new FileInMemory(filePath.getFileName().toString());
        file.addLine(line);
        files.add(file);
    }

    @Override
    public int getFileCount() {
        return files.size();
    }


    @Getter
    private static class FileInMemory {

        private final String name;
        private final List<String> lines;

        FileInMemory(String name, List<String> lines) {
            this.name = name;
            this.lines = Objects.requireNonNullElseGet(lines, ArrayList::new);
        }

        FileInMemory(String name) {
            this(name, null);
        }

        void addLine(String line) {
            this.lines.add(line);
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(files);
    }

}
