package com.lzb.unit_test;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

/**
 * <br/>
 * Created on : 2023-07-17 10:31
 * @author lizebin
 */
@RequiredArgsConstructor
public class AuditAppService {

    private final AuditManager auditManager;

    private final FileSystem fileSystem;

    void visit(String visitor, LocalDateTime visitDateTime) {
        UpdateFile updateFile = auditManager.visit(visitor, visitDateTime, fileSystem);
        fileSystem.applyUpdateFile(updateFile);
    }
}
