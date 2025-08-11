package com.example.tlstool.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName connection_detail
 */
@TableName(value ="connection_detail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionDetailPO {
    /**
     * 
     */
    @TableId(value = "connection_id", type = IdType.AUTO)
    private Long connectionId;

    /**
     * 
     */
    @TableField(value = "target_id")
    private Long targetId;

    /**
     * 最高支持的TLS版本
     */
    @TableField(value = "highest_tls_version")
    private String highestTlsVersion;

    /**
     * 协商的密码套件
     */
    @TableField(value = "cipher_suite_supported")
    private String cipherSuiteSupported;

    /**
     * DISABLED/OPTIONAL/REQUIRED
     */
    @TableField(value = "client_auth_requirement")
    private String clientAuthRequirement;

    /**
     * 0：fasle，1：true
     */
    @TableField(value = "supports_ecdh_key_exchange")
    private Integer supportsEcdhKeyExchange;

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
        ConnectionDetailPO other = (ConnectionDetailPO) that;
        return (this.getConnectionId() == null ? other.getConnectionId() == null : this.getConnectionId().equals(other.getConnectionId()))
            && (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
            && (this.getHighestTlsVersion() == null ? other.getHighestTlsVersion() == null : this.getHighestTlsVersion().equals(other.getHighestTlsVersion()))
            && (this.getCipherSuiteSupported() == null ? other.getCipherSuiteSupported() == null : this.getCipherSuiteSupported().equals(other.getCipherSuiteSupported()))
            && (this.getClientAuthRequirement() == null ? other.getClientAuthRequirement() == null : this.getClientAuthRequirement().equals(other.getClientAuthRequirement()))
            && (this.getSupportsEcdhKeyExchange() == null ? other.getSupportsEcdhKeyExchange() == null : this.getSupportsEcdhKeyExchange().equals(other.getSupportsEcdhKeyExchange()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getConnectionId() == null) ? 0 : getConnectionId().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getHighestTlsVersion() == null) ? 0 : getHighestTlsVersion().hashCode());
        result = prime * result + ((getCipherSuiteSupported() == null) ? 0 : getCipherSuiteSupported().hashCode());
        result = prime * result + ((getClientAuthRequirement() == null) ? 0 : getClientAuthRequirement().hashCode());
        result = prime * result + ((getSupportsEcdhKeyExchange() == null) ? 0 : getSupportsEcdhKeyExchange().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", connectionId=").append(connectionId);
        sb.append(", targetId=").append(targetId);
        sb.append(", highestTlsVersion=").append(highestTlsVersion);
        sb.append(", cipherSuiteSupported=").append(cipherSuiteSupported);
        sb.append(", clientAuthRequirement=").append(clientAuthRequirement);
        sb.append(", supportsEcdh=").append(supportsEcdhKeyExchange);
        sb.append("]");
        return sb.toString();
    }
}