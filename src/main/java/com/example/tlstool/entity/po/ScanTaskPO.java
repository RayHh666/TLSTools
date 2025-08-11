package com.example.tlstool.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName scan_task
 */
@TableName(value ="scan_task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScanTaskPO {
    /**
     * 
     */
    @TableId(value = "task_id", type = IdType.AUTO)
    private Long taskId;

    /**
     * 任务名称
     */
    @TableField(value = "task_name")
    private String taskName;

    /**
     * 任务类型: 'TLS_SCAN','HTTP_SCAN','STARTTLS_SCAN'
     */
    @TableField(value = "task_type")
    private String taskType;

    @TableField(value = "targets")
    private String targets;

    /**
     * 工具名称
     */
    @TableField(value = "tool_name")
    private String toolName;

    /**
     * 工具版本
     */
    @TableField(value = "tool_version")
    private String toolVersion;

    /**
     * 任务状态：'PENDING','RUNNING','COMPLETED','FAILED','ERROR_NO_CONNECTIVITY'
     */
    @TableField(value = "status")
    private String status;

    /**
     * 备注信息
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 任务创建时间
     */
    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    /**
     * 扫描开始时间
     */
    @TableField(value = "started_at")
    private LocalDateTime startedAt;

    /**
     * 扫描结束时间
     */
    @TableField(value = "completed_at")
    private LocalDateTime completedAt;

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
        ScanTaskPO other = (ScanTaskPO) that;
        return (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getTaskType() == null ? other.getTaskType() == null : this.getTaskType().equals(other.getTaskType()))
            && (this.getToolName() == null ? other.getToolName() == null : this.getToolName().equals(other.getToolName()))
            && (this.getToolVersion() == null ? other.getToolVersion() == null : this.getToolVersion().equals(other.getToolVersion()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getStartedAt() == null ? other.getStartedAt() == null : this.getStartedAt().equals(other.getStartedAt()))
            && (this.getCompletedAt() == null ? other.getCompletedAt() == null : this.getCompletedAt().equals(other.getCompletedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getTaskType() == null) ? 0 : getTaskType().hashCode());
        result = prime * result + ((getToolName() == null) ? 0 : getToolName().hashCode());
        result = prime * result + ((getToolVersion() == null) ? 0 : getToolVersion().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getStartedAt() == null) ? 0 : getStartedAt().hashCode());
        result = prime * result + ((getCompletedAt() == null) ? 0 : getCompletedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", taskType=").append(taskType);
        sb.append(", toolName=").append(toolName);
        sb.append(", toolVersion=").append(toolVersion);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", startedAt=").append(startedAt);
        sb.append(", completedAt=").append(completedAt);
        sb.append("]");
        return sb.toString();
    }
}