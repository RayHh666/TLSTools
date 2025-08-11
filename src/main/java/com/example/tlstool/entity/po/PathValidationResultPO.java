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
 * @TableName path_validation_result
 */
@TableName(value ="path_validation_result")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PathValidationResultPO {
    /**
     * path_validation_result_id
     */
    @TableId(value = "path_validation_result_id", type = IdType.AUTO)
    private Long pathValidationResultId;

    /**
     * 关联证书部署id
     */
    @TableField(value = "certificate_deployment_id")
    private Long certificateDeploymentId;

    /**
     * 信任库路径
     */
    @TableField(value = "trust_store_path")
    private String trustStorePath;

    /**
     * 信任库名称
     */
    @TableField(value = "trust_store_name")
    private String trustStoreName;

    /**
     * 信任库版本
     */
    @TableField(value = "trust_store_version")
    private String trustStoreVersion;

    /**
     * 验证错误信息
     */
    @TableField(value = "validation_error")
    private String validationError;

    /**
     * 验证是否成功 0：false；1：true
     */
    @TableField(value = "was_validation_successful")
    private Integer wasValidationSuccessful;

    /**
     * 
     */
    @TableField(value = "trust_store_ev_oids")
    private String trustStoreEvOids;

    /**
     * 在path_validation_results中的索引
     */
    @TableField(value = "path_validation_result_index")
    private Integer pathValidationResultIndex;

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
        PathValidationResultPO other = (PathValidationResultPO) that;
        return (this.getPathValidationResultId() == null ? other.getPathValidationResultId() == null : this.getPathValidationResultId().equals(other.getPathValidationResultId()))
            && (this.getCertificateDeploymentId() == null ? other.getCertificateDeploymentId() == null : this.getCertificateDeploymentId().equals(other.getCertificateDeploymentId()))
            && (this.getTrustStorePath() == null ? other.getTrustStorePath() == null : this.getTrustStorePath().equals(other.getTrustStorePath()))
            && (this.getTrustStoreName() == null ? other.getTrustStoreName() == null : this.getTrustStoreName().equals(other.getTrustStoreName()))
            && (this.getTrustStoreVersion() == null ? other.getTrustStoreVersion() == null : this.getTrustStoreVersion().equals(other.getTrustStoreVersion()))
            && (this.getValidationError() == null ? other.getValidationError() == null : this.getValidationError().equals(other.getValidationError()))
            && (this.getWasValidationSuccessful() == null ? other.getWasValidationSuccessful() == null : this.getWasValidationSuccessful().equals(other.getWasValidationSuccessful()))
            && (this.getTrustStoreEvOids() == null ? other.getTrustStoreEvOids() == null : this.getTrustStoreEvOids().equals(other.getTrustStoreEvOids()))
            && (this.getPathValidationResultIndex() == null ? other.getPathValidationResultIndex() == null : this.getPathValidationResultIndex().equals(other.getPathValidationResultIndex()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPathValidationResultId() == null) ? 0 : getPathValidationResultId().hashCode());
        result = prime * result + ((getCertificateDeploymentId() == null) ? 0 : getCertificateDeploymentId().hashCode());
        result = prime * result + ((getTrustStorePath() == null) ? 0 : getTrustStorePath().hashCode());
        result = prime * result + ((getTrustStoreName() == null) ? 0 : getTrustStoreName().hashCode());
        result = prime * result + ((getTrustStoreVersion() == null) ? 0 : getTrustStoreVersion().hashCode());
        result = prime * result + ((getValidationError() == null) ? 0 : getValidationError().hashCode());
        result = prime * result + ((getWasValidationSuccessful() == null) ? 0 : getWasValidationSuccessful().hashCode());
        result = prime * result + ((getTrustStoreEvOids() == null) ? 0 : getTrustStoreEvOids().hashCode());
        result = prime * result + ((getPathValidationResultIndex() == null) ? 0 : getPathValidationResultIndex().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pathValidationResultId=").append(pathValidationResultId);
        sb.append(", certificateDeploymentId=").append(certificateDeploymentId);
        sb.append(", trustStorePath=").append(trustStorePath);
        sb.append(", trustStoreName=").append(trustStoreName);
        sb.append(", trustStoreVersion=").append(trustStoreVersion);
        sb.append(", validationError=").append(validationError);
        sb.append(", wasValidationSuccessful=").append(wasValidationSuccessful);
        sb.append(", trustStoreEvOids=").append(trustStoreEvOids);
        sb.append(", index=").append(pathValidationResultIndex);
        sb.append("]");
        return sb.toString();
    }
}