package com.lzb.unit_test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-07-16 11:07
 * @author lizebin
 */
public class FileSystemInMemory implements FileSystem {

    private static final ConcurrentHashMap<String, List<FileInMemory>> dir2Paths = new ConcurrentHashMap<>();

    @Override
    public List<String> readAllLines(Path filePath) {
        if (filePath.toFile().isFile()) {
            try {
                return Files.readAllLines(filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }
        return Optional.ofNullable(dir2Paths.get(filePath.getParent().toString()))
                .orElse(Collections.emptyList())
                .stream()
                .map(FileInMemory::getLines)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public void createFile(Path filePath) {
        dir2Paths.computeIfAbsent(filePath.getParent().toString(), k -> new ArrayList<>())
                .add(new FileInMemory(filePath.getFileName().toString()));
    }

    @Override
    public void writeLine(Path filePath, String line) {
        String dir = filePath.getParent().toString();
        List<FileInMemory> files = dir2Paths.get(dir);
        if (Objects.isNull(files)) {
            createFile(filePath);
            files = dir2Paths.get(dir);
        }
        String fileName = filePath.getFileName().toString();
        files.forEach(file -> {
            if (Objects.equals(file.getName(), fileName)) {
                file.getLines().add(line);
            }
        });
    }

    @Override
    public List<Path> readAllFiles(Path dir) {
        return Optional.ofNullable(dir2Paths.get(dir.toString()))
                .orElse(Collections.emptyList())
                .stream()
                .map(f -> f.getPath(dir))
                .toList();
    }

    @Getter
    private static class FileInMemory {

        private final String name;
        private final List<String> lines;

        FileInMemory(String name, List<String> lines) {
            this.name = name;
            this.lines = lines;
        }

        FileInMemory(String name) {
            this(name, new ArrayList<>());
        }

        public Path getPath(Path dir) {
            return Paths.get(dir.toString(), name);
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(dir2Paths);
    }

}
