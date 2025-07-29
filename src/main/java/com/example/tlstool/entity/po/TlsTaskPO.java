package com.example.tlstool.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName t_tls_task
 */
@TableName(value ="t_tls_task")
@Data
public class TlsTaskPO {
    /**
     * task_id
     */
    @TableId(value = "id")
    private String id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 检测工具 1-sslyze 2-sslscan 3-testssl
     */
    @TableField(value = "detection_tool")
    private Integer detectionTool;

    /**
     * TLS协议 多个协议以逗号分割
     */
    @TableField(value = "tls_protocol")
    private String tlsProtocol;

    /**
     * 邮件协议 多个协议以逗号分割
     */
    @TableField(value = "mail_protocol")
    private String mailProtocol;

    /**
     * 是否开启HTTP安全头协议检测 0-否 1-是
     */
    @TableField(value = "is_http_security_header_detection")
    private Integer isHttpSecurityHeaderDetection;

    /**
     * 所属版本
     */
    @TableField(value = "version")
    private String version;

    /**
     * 是否创建报告
     */
    @TableField(value = "is_create_report")
    private Integer isCreateReport;

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
     * 创建人
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    private LocalDateTime endTime;

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
            && (this.getTlsProtocol() == null ? other.getTlsProtocol() == null : this.getTlsProtocol().equals(other.getTlsProtocol()))
            && (this.getMailProtocol() == null ? other.getMailProtocol() == null : this.getMailProtocol().equals(other.getMailProtocol()))
            && (this.getIsHttpSecurityHeaderDetection() == null ? other.getIsHttpSecurityHeaderDetection() == null : this.getIsHttpSecurityHeaderDetection().equals(other.getIsHttpSecurityHeaderDetection()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getIsCreateReport() == null ? other.getIsCreateReport() == null : this.getIsCreateReport().equals(other.getIsCreateReport()))
            && (this.getProgress() == null ? other.getProgress() == null : this.getProgress().equals(other.getProgress()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTaskName() == null) ? 0 : getTaskName().hashCode());
        result = prime * result + ((getDetectionTool() == null) ? 0 : getDetectionTool().hashCode());
        result = prime * result + ((getTlsProtocol() == null) ? 0 : getTlsProtocol().hashCode());
        result = prime * result + ((getMailProtocol() == null) ? 0 : getMailProtocol().hashCode());
        result = prime * result + ((getIsHttpSecurityHeaderDetection() == null) ? 0 : getIsHttpSecurityHeaderDetection().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getIsCreateReport() == null) ? 0 : getIsCreateReport().hashCode());
        result = prime * result + ((getProgress() == null) ? 0 : getProgress().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
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
        sb.append(", tlsProtocol=").append(tlsProtocol);
        sb.append(", mailProtocol=").append(mailProtocol);
        sb.append(", isHttpSecurityHeaderDetection=").append(isHttpSecurityHeaderDetection);
        sb.append(", version=").append(version);
        sb.append(", isCreateReport=").append(isCreateReport);
        sb.append(", progress=").append(progress);
        sb.append(", state=").append(state);
        sb.append(", createBy=").append(createBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", endTime=").append(endTime);
        sb.append("]");
        return sb.toString();
    }
}