package com.lzb.unit_test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

/**
 * <br/>
 * Created on : 2023-07-15 23:03
 * @author lizebin
 */
@ExtendWith(MockitoExtension.class)
class AuditManagerUnitTest {

    private FileSystem fileSystem;

    private AuditManager sut;

    @BeforeEach
    public void setup() {
        fileSystem = spy(FileSystemInMemory.class);
        sut = new AuditManager(fileSystem);
    }

    @Test
    @DisplayName("第一个访问者创建第一个文件")
    void should_append_one_line_when_first_file_created() {

        // when
        Path path = sut.visit("lizebin", LocalDateTime.now());

        // then
        Assertions.assertEquals( "audit_01.txt", path.getFileName().toString());
    }

    @Test
    @DisplayName("读取文件内容")
    void should_when_return_visitors_read_lines() throws IOException {

        // given
        String visitor = "lizebin";
        LocalDateTime visitDateTime = LocalDateTime.now();
        Path path = Paths.get("");
        doReturn(List.of(visitor + "; " + visitDateTime)).when(fileSystem).readAllLines(path);

        // when
        List<String> visitors = sut.listVisitors(path);

        // then
        Assertions.assertEquals(1, visitors.size());
        Assertions.assertEquals(visitor + "; " + visitDateTime, visitors.get(0));
    }

    @Test
    @DisplayName("写多个visitor")
    void should_return_new_file_when_write_multiple_visitor() {
        for (int i = 0; i < 5; i++) {
            String visitor = "lizebin" + i;
            LocalDateTime visitDateTime = LocalDateTime.now().plusSeconds(i);
            sut.visit(visitor, visitDateTime);
        }

        Assertions.assertEquals(2, sut.getFileSystem().getFileCount());
        Assertions.assertEquals(3, sut.getFileSystem().readAllLines(Paths.get("audit_01.txt")).size());
        Assertions.assertEquals(2, sut.getFileSystem().readAllLines(Paths.get("audit_02.txt")).size());
    }

}
