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
 * @TableName cipher_suite_support
 */
@TableName(value ="cipher_suite_support")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CipherSuiteSupportPO {
    /**
     * 密钥id
     */
    @TableId(value = "cipher_id", type = IdType.AUTO)
    private Long cipherId;

    /**
     * 目标id
     */
    @TableField(value = "target_id")
    private Long targetId;

    /**
     * TLS版本
     */
    @TableField(value = "tls_version")
    private String tlsVersion;

    /**
     * 密钥名称
     */
    @TableField(value = "cipher_name")
    private String cipherName;

    /**
     * 是否被接受
     */
    @TableField(value = "is_accepted")
    private Integer isAccepted;

    /**
     * 
     */
    @TableField(value = "key_size")
    private Integer keySize;

    /**
     * 是否支持前向加密 0：不支持，1：支持
     */
    @TableField(value = "forward_secrecy")
    private Integer forwardSecrecy;

    /**
     * 密钥强度
     */
    @TableField(value = "rating")
    private String rating;

    /**
     * openssl名称
     */
    @TableField(value = "openssl_name")
    private String opensslName;

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
        CipherSuiteSupportPO other = (CipherSuiteSupportPO) that;
        return (this.getCipherId() == null ? other.getCipherId() == null : this.getCipherId().equals(other.getCipherId()))
            && (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
            && (this.getTlsVersion() == null ? other.getTlsVersion() == null : this.getTlsVersion().equals(other.getTlsVersion()))
            && (this.getCipherName() == null ? other.getCipherName() == null : this.getCipherName().equals(other.getCipherName()))
            && (this.getIsAccepted() == null ? other.getIsAccepted() == null : this.getIsAccepted().equals(other.getIsAccepted()))
            && (this.getKeySize() == null ? other.getKeySize() == null : this.getKeySize().equals(other.getKeySize()))
            && (this.getForwardSecrecy() == null ? other.getForwardSecrecy() == null : this.getForwardSecrecy().equals(other.getForwardSecrecy()))
            && (this.getRating() == null ? other.getRating() == null : this.getRating().equals(other.getRating()))
            && (this.getOpensslName() == null ? other.getOpensslName() == null : this.getOpensslName().equals(other.getOpensslName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCipherId() == null) ? 0 : getCipherId().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getTlsVersion() == null) ? 0 : getTlsVersion().hashCode());
        result = prime * result + ((getCipherName() == null) ? 0 : getCipherName().hashCode());
        result = prime * result + ((getIsAccepted() == null) ? 0 : getIsAccepted().hashCode());
        result = prime * result + ((getKeySize() == null) ? 0 : getKeySize().hashCode());
        result = prime * result + ((getForwardSecrecy() == null) ? 0 : getForwardSecrecy().hashCode());
        result = prime * result + ((getRating() == null) ? 0 : getRating().hashCode());
        result = prime * result + ((getOpensslName() == null) ? 0 : getOpensslName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cipherId=").append(cipherId);
        sb.append(", targetId=").append(targetId);
        sb.append(", tlsVersion=").append(tlsVersion);
        sb.append(", cipherName=").append(cipherName);
        sb.append(", isAccepted=").append(isAccepted);
        sb.append(", keySize=").append(keySize);
        sb.append(", forwardSecrecy=").append(forwardSecrecy);
        sb.append(", rating=").append(rating);
        sb.append(", opensslName=").append(opensslName);
        sb.append("]");
        return sb.toString();
    }
}