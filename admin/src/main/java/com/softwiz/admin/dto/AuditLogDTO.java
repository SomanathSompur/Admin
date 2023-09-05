package com.softwiz.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditLogDTO {

    private Long id;
    private Long adminId;
    private String action;
    private String entityType;
    private Long entityId;
    private LocalDateTime timestamp;

    @Override
    public String toString() {
        return "AuditLogDTO{" +
                "id=" + id +
                ", adminId=" + adminId +
                ", action='" + action + '\'' +
                ", entityType='" + entityType + '\'' +
                ", entityId=" + entityId +
                ", timestamp=" + timestamp +
                '}';
    }
}
