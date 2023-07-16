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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * <br/>
 * Created on : 2023-07-15 23:03
 * @author lizebin
 */
@ExtendWith(MockitoExtension.class)
class AuditManagerUnitTest {

    @Spy
    private FileSystem fileSystem = new FileSystemInMemory();

    private AuditManager sut;

    @BeforeEach
    public void setup() {
        sut = new AuditManager(fileSystem);
    }

    @Test
    @DisplayName("第一个访问者创建第一个文件")
    void should_append_one_line_when_first_file_created() {

        // when
        Path path = sut.visit("lizebin", LocalDateTime.now());

        // then
        Assertions.assertEquals(path.toAbsolutePath(), Paths.get(sut.getDir() + "audit_01.txt"));
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
    void should_return_first_visitor_when_visit() {

        // given
        String visitor = "lizebin";
        LocalDateTime visitDateTime = LocalDateTime.now();
        Path path = sut.visit(visitor, visitDateTime);

        // when
        List<String> visitors = sut.listVisitors(path);

        // then
        Assertions.assertEquals(1, visitors.size());
        Assertions.assertEquals(visitor + "; " + visitDateTime, visitors.get(0));

    }

    @Test
    @DisplayName("写多个visitor")
    void should_return_new_file_when_write_multiple_visitor() {
        // given
        for (int i = 0; i < 5; i++) {
            String visitor = "lizebin" + i;
            LocalDateTime visitDateTime = LocalDateTime.now().plusSeconds(i);
            sut.visit(visitor, visitDateTime);
        }

        // when
        Assertions.assertEquals(2, sut.getFileSize());

        // then

    }

}
