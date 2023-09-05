package com.softwiz.admin.controller;

import com.softwiz.admin.entity.AuditLog;
import com.softwiz.admin.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/audit_logs")
public class AuditLogController {
    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/{adminId}")
    public List<AuditLog> getAllLogsByAdmin(@PathVariable Long adminId) {
        return auditLogService.getAllLogsByAdmin(adminId);
    }
}
