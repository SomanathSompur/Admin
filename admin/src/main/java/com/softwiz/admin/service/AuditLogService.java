package com.softwiz.admin.service;

import com.softwiz.admin.entity.AuditLog;
import com.softwiz.admin.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;
    public void addLog(Long adminId, String action, String entityType, Long entityId) {

        AuditLog log = new AuditLog();
        log.setAdminId(adminId);
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
    public List<AuditLog> getAllLogsByAdmin(Long adminId) {
        return auditLogRepository.findByAdminId(adminId);
    }
}
