package com.example.tlstool.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.tlstool.handler.JsonNodeTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName scan_target
 */
@TableName(value ="scan_target")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScanTargetPO {
    /**
     * 目标id
     */
    @TableId(value = "target_id", type = IdType.AUTO)
    private Long targetId;

    /**
     * 任务id
     */
    @TableField(value = "task_id")
    private Long taskId;

    /**
     * 目标主机(域名或IP)
     */
    @TableField(value = "host")
    private String host;

    /**
     * 目标端口
     */
    @TableField(value = "port")
    private Integer port;

    /**
     * 解析后的IP地址
     */
    @TableField(value = "resolved_ip")
    private String resolvedIp;

    /**
     * 连接类型：'DIRECT','HTTP_PROXY','SOCKS'
     */
    @TableField(value = "connection_type")
    private String connectionType;

    /**
     * 代理配置
     */
    @TableField(value = "proxy_settings" ,typeHandler = JsonNodeTypeHandler.class)
    private JsonNode proxySettings;

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
        ScanTargetPO other = (ScanTargetPO) that;
        return (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getHost() == null ? other.getHost() == null : this.getHost().equals(other.getHost()))
            && (this.getPort() == null ? other.getPort() == null : this.getPort().equals(other.getPort()))
            && (this.getResolvedIp() == null ? other.getResolvedIp() == null : this.getResolvedIp().equals(other.getResolvedIp()))
            && (this.getConnectionType() == null ? other.getConnectionType() == null : this.getConnectionType().equals(other.getConnectionType()))
            && (this.getProxySettings() == null ? other.getProxySettings() == null : this.getProxySettings().equals(other.getProxySettings()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getHost() == null) ? 0 : getHost().hashCode());
        result = prime * result + ((getPort() == null) ? 0 : getPort().hashCode());
        result = prime * result + ((getResolvedIp() == null) ? 0 : getResolvedIp().hashCode());
        result = prime * result + ((getConnectionType() == null) ? 0 : getConnectionType().hashCode());
        result = prime * result + ((getProxySettings() == null) ? 0 : getProxySettings().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", targetId=").append(targetId);
        sb.append(", taskId=").append(taskId);
        sb.append(", host=").append(host);
        sb.append(", port=").append(port);
        sb.append(", resolvedIp=").append(resolvedIp);
        sb.append(", connectionType=").append(connectionType);
        sb.append(", proxySettings=").append(proxySettings);
        sb.append("]");
        return sb.toString();
    }
}