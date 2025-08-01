package com.example.tlstool.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.example.tlstool.handler.JsonNodeTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName t_tls_task
 */
@TableName(value ="t_tls_task", autoResultMap = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TlsTaskPO {
    /**
     * task_id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 扫描目标 多个目标以逗号分隔
     */
    @TableField(value = "targets")
    private String targets;

    /**
     * 检测工具 1-sslyze 2-sslscan 3-testssl
     */
    @TableField(value = "detection_tool")
    private Integer detectionTool;

    /**
     * TLS协议 多个协议以逗号分割
     */
    @TableField(value = "tls_protocols")
    private String tlsProtocols;

    /**
     * 邮件协议 多个协议以逗号分割
     */
    @TableField(value = "mail_protocols")
    private String mailProtocols;

    /**
     * 是否开启HTTP安全头协议检测 0-否 1-是
     */
    @TableField(value = "is_http_security_header_detection")
    private Integer isHttpSecurityHeaderDetection;

    /**
     * 扫描进度
     */
    @TableField(value = "progress")
    private Integer progress;

    /**
     * 状态：0：新建、1：等待调度、2：正在扫描、3:暂停、4：停止、5：结束
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 无效目标
     */
    @TableField(value = "invalid_server_strings" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode invalidServerStrings;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    @TableField(value = "date_scans_started")
    private LocalDateTime dateScansStarted;

    /**
     * 结束时间
     */
    @TableField(value = "date_scans_completed")
    private LocalDateTime dateScansCompleted;

    @TableField(value = "detection_tool_version")
    private String detectionToolVersion;

    @TableField(value = "remark")
    private String remark;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TlsTaskPO other = (TlsTaskPO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTaskName() == null ? other.getTaskName() == null : this.getTaskName().equals(other.getTaskName()))
            && (this.getDetectionTool() == null ? other.getDetectionTool() == null : this.getDetectionTool().equals(other.getDetectionTool()))
            && (this.getTlsProtocols() == null ? other.getTlsProtocols() == null : this.getTlsProtocols().equals(other.getTlsProtocols()))
            && (this.getMailProtocols() == null ? other.getMailProtocols() == null : this.getMailProtocols().equals(other.getMailProtocols()))
            && (this.getIsHttpSecurityHeaderDetection() == null ? other.getIsHttpSecurityHeaderDetection() == null : this.getIsHttpSecurityHeaderDetection().equals(other.getIsHttpSecurityHeaderDetection()))
            && (this.getProgress() == null ? other.getProgress() == null : this.getProgress().equals(other.getProgress()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getDateScansStarted() == null ? other.getDateScansStarted() == null : this.getDateScansStarted().equals(other.getDateScansStarted()))
            && (this.getDateScansCompleted() == null ? other.getDateScansCompleted() == null : this.getDateScansCompleted().equals(other.getDateScansCompleted()))
            && (this.getDetectionToolVersion() == null ? other.getDetectionToolVersion() == null : this.getDetectionToolVersion().equals(other.getDetectionToolVersion()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTaskName() == null) ? 0 : getTaskName().hashCode());
        result = prime * result + ((getDetectionTool() == null) ? 0 : getDetectionTool().hashCode());
        result = prime * result + ((getTlsProtocols() == null) ? 0 : getTlsProtocols().hashCode());
        result = prime * result + ((getMailProtocols() == null) ? 0 : getMailProtocols().hashCode());
        result = prime * result + ((getIsHttpSecurityHeaderDetection() == null) ? 0 : getIsHttpSecurityHeaderDetection().hashCode());
        result = prime * result + ((getProgress() == null) ? 0 : getProgress().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getDateScansStarted() == null) ? 0 : getDateScansStarted().hashCode());
        result = prime * result + ((getDateScansCompleted() == null) ? 0 : getDateScansCompleted().hashCode());
        result = prime * result + ((getDetectionToolVersion() == null) ? 0 : getDetectionToolVersion().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", taskName=").append(taskName);
        sb.append(", detectionTool=").append(detectionTool);
        sb.append(", tlsProtocols=").append(tlsProtocols);
        sb.append(", mailProtocols=").append(mailProtocols);
        sb.append(", isHttpSecurityHeaderDetection=").append(isHttpSecurityHeaderDetection);
        sb.append(", progress=").append(progress);
        sb.append(", state=").append(state);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", dateScansStarted=").append(dateScansStarted);
        sb.append(", dateScansCompleted=").append(dateScansCompleted);
        sb.append(", detectionToolVersion=").append(detectionToolVersion);
        sb.append(", remark=").append(remark);
        sb.append("]");
        return sb.toString();
    }
}