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
 * @TableName certificate_deployment
 */
@TableName(value ="certificate_deployment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDeploymentPO {
    /**
     * 
     */
    @TableId(value = "certificate_deployment_id", type = IdType.AUTO)
    private Long certificateDeploymentId;

    /**
     * 
     */
    @TableField(value = "target_id")
    private Long targetId;

    /**
     * 0：false，1：true
     */
    @TableField(value = "leaf_certificate_has_must_staple_extension")
    private Integer leafCertificateHasMustStapleExtension;

    /**
     * 0：false，1：true
     */
    @TableField(value = "leaf_certificate_is_ev")
    private Integer leafCertificateIsEv;

    /**
     * 
     */
    @TableField(value = "leaf_certificate_signed_certificate_timestamps_count")
    private Integer leafCertificateSignedCertificateTimestampsCount;

    /**
     * 0：false，1：true
     */
    @TableField(value = "received_chain_contains_anchor_certificate")
    private Integer receivedChainContainsAnchorCertificate;

    /**
     * 0：false，1：true
     */
    @TableField(value = "received_chain_has_valid_order")
    private Integer receivedChainHasValidOrder;

    /**
     * 0：false，1：true
     */
    @TableField(value = "verified_chain_has_sha1_signature")
    private Integer verifiedChainHasSha1Signature;

    /**
     * 0：false，1：true
     */
    @TableField(value = "verified_chain_has_legacy_symantec_anchor")
    private Integer verifiedChainHasLegacySymantecAnchor;

    /**
     * 
     */
    @TableField(value = "ocsp_response_response_status")
    private String ocspResponseResponseStatus;

    /**
     * 
     */
    @TableField(value = "ocsp_response_certificate_status")
    private String ocspResponseCertificateStatus;

    /**
     * 
     */
    @TableField(value = "ocsp_response_revocation_time")
    private LocalDateTime ocspResponseRevocationTime;

    /**
     * 
     */
    @TableField(value = "ocsp_response_produced_at")
    private LocalDateTime ocspResponseProducedAt;

    /**
     * 
     */
    @TableField(value = "ocsp_response_this_update")
    private LocalDateTime ocspResponseThisUpdate;

    /**
     * 
     */
    @TableField(value = "ocsp_response_next_update")
    private LocalDateTime ocspResponseNextUpdate;

    /**
     * 
     */
    @TableField(value = "ocsp_response_serial_number")
    private Long ocspResponseSerialNumber;

    /**
     * 0：false，1：true
     */
    @TableField(value = "ocsp_response_is_trusted")
    private Integer ocspResponseIsTrusted;

    /**
     * certificate_deployment中的索引
     */
    @TableField(value = "certificate_deployment_index")
    private Integer certificateDeploymentIndex;

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
        CertificateDeploymentPO other = (CertificateDeploymentPO) that;
        return (this.getCertificateDeploymentId() == null ? other.getCertificateDeploymentId() == null : this.getCertificateDeploymentId().equals(other.getCertificateDeploymentId()))
            && (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
            && (this.getLeafCertificateHasMustStapleExtension() == null ? other.getLeafCertificateHasMustStapleExtension() == null : this.getLeafCertificateHasMustStapleExtension().equals(other.getLeafCertificateHasMustStapleExtension()))
            && (this.getLeafCertificateIsEv() == null ? other.getLeafCertificateIsEv() == null : this.getLeafCertificateIsEv().equals(other.getLeafCertificateIsEv()))
            && (this.getLeafCertificateSignedCertificateTimestampsCount() == null ? other.getLeafCertificateSignedCertificateTimestampsCount() == null : this.getLeafCertificateSignedCertificateTimestampsCount().equals(other.getLeafCertificateSignedCertificateTimestampsCount()))
            && (this.getReceivedChainContainsAnchorCertificate() == null ? other.getReceivedChainContainsAnchorCertificate() == null : this.getReceivedChainContainsAnchorCertificate().equals(other.getReceivedChainContainsAnchorCertificate()))
            && (this.getReceivedChainHasValidOrder() == null ? other.getReceivedChainHasValidOrder() == null : this.getReceivedChainHasValidOrder().equals(other.getReceivedChainHasValidOrder()))
            && (this.getVerifiedChainHasSha1Signature() == null ? other.getVerifiedChainHasSha1Signature() == null : this.getVerifiedChainHasSha1Signature().equals(other.getVerifiedChainHasSha1Signature()))
            && (this.getVerifiedChainHasLegacySymantecAnchor() == null ? other.getVerifiedChainHasLegacySymantecAnchor() == null : this.getVerifiedChainHasLegacySymantecAnchor().equals(other.getVerifiedChainHasLegacySymantecAnchor()))
            && (this.getOcspResponseResponseStatus() == null ? other.getOcspResponseResponseStatus() == null : this.getOcspResponseResponseStatus().equals(other.getOcspResponseResponseStatus()))
            && (this.getOcspResponseCertificateStatus() == null ? other.getOcspResponseCertificateStatus() == null : this.getOcspResponseCertificateStatus().equals(other.getOcspResponseCertificateStatus()))
            && (this.getOcspResponseRevocationTime() == null ? other.getOcspResponseRevocationTime() == null : this.getOcspResponseRevocationTime().equals(other.getOcspResponseRevocationTime()))
            && (this.getOcspResponseProducedAt() == null ? other.getOcspResponseProducedAt() == null : this.getOcspResponseProducedAt().equals(other.getOcspResponseProducedAt()))
            && (this.getOcspResponseThisUpdate() == null ? other.getOcspResponseThisUpdate() == null : this.getOcspResponseThisUpdate().equals(other.getOcspResponseThisUpdate()))
            && (this.getOcspResponseNextUpdate() == null ? other.getOcspResponseNextUpdate() == null : this.getOcspResponseNextUpdate().equals(other.getOcspResponseNextUpdate()))
            && (this.getOcspResponseSerialNumber() == null ? other.getOcspResponseSerialNumber() == null : this.getOcspResponseSerialNumber().equals(other.getOcspResponseSerialNumber()))
            && (this.getOcspResponseIsTrusted() == null ? other.getOcspResponseIsTrusted() == null : this.getOcspResponseIsTrusted().equals(other.getOcspResponseIsTrusted()))
            && (this.getCertificateDeploymentIndex() == null ? other.getCertificateDeploymentIndex() == null : this.getCertificateDeploymentIndex().equals(other.getCertificateDeploymentIndex()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCertificateDeploymentId() == null) ? 0 : getCertificateDeploymentId().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getLeafCertificateHasMustStapleExtension() == null) ? 0 : getLeafCertificateHasMustStapleExtension().hashCode());
        result = prime * result + ((getLeafCertificateIsEv() == null) ? 0 : getLeafCertificateIsEv().hashCode());
        result = prime * result + ((getLeafCertificateSignedCertificateTimestampsCount() == null) ? 0 : getLeafCertificateSignedCertificateTimestampsCount().hashCode());
        result = prime * result + ((getReceivedChainContainsAnchorCertificate() == null) ? 0 : getReceivedChainContainsAnchorCertificate().hashCode());
        result = prime * result + ((getReceivedChainHasValidOrder() == null) ? 0 : getReceivedChainHasValidOrder().hashCode());
        result = prime * result + ((getVerifiedChainHasSha1Signature() == null) ? 0 : getVerifiedChainHasSha1Signature().hashCode());
        result = prime * result + ((getVerifiedChainHasLegacySymantecAnchor() == null) ? 0 : getVerifiedChainHasLegacySymantecAnchor().hashCode());
        result = prime * result + ((getOcspResponseResponseStatus() == null) ? 0 : getOcspResponseResponseStatus().hashCode());
        result = prime * result + ((getOcspResponseCertificateStatus() == null) ? 0 : getOcspResponseCertificateStatus().hashCode());
        result = prime * result + ((getOcspResponseRevocationTime() == null) ? 0 : getOcspResponseRevocationTime().hashCode());
        result = prime * result + ((getOcspResponseProducedAt() == null) ? 0 : getOcspResponseProducedAt().hashCode());
        result = prime * result + ((getOcspResponseThisUpdate() == null) ? 0 : getOcspResponseThisUpdate().hashCode());
        result = prime * result + ((getOcspResponseNextUpdate() == null) ? 0 : getOcspResponseNextUpdate().hashCode());
        result = prime * result + ((getOcspResponseSerialNumber() == null) ? 0 : getOcspResponseSerialNumber().hashCode());
        result = prime * result + ((getOcspResponseIsTrusted() == null) ? 0 : getOcspResponseIsTrusted().hashCode());
        result = prime * result + ((getCertificateDeploymentIndex() == null) ? 0 : getCertificateDeploymentIndex().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", certificateDeploymentId=").append(certificateDeploymentId);
        sb.append(", targetId=").append(targetId);
        sb.append(", leafCertificateHasMustStapleExtension=").append(leafCertificateHasMustStapleExtension);
        sb.append(", leafCertificateIsEv=").append(leafCertificateIsEv);
        sb.append(", leafCertificateSignedCertificateTimestampsCount=").append(leafCertificateSignedCertificateTimestampsCount);
        sb.append(", receivedChainContainsAnchorCertificate=").append(receivedChainContainsAnchorCertificate);
        sb.append(", receivedChainHasValidOrder=").append(receivedChainHasValidOrder);
        sb.append(", verifiedChainHasSha1Signature=").append(verifiedChainHasSha1Signature);
        sb.append(", verifiedChainHasLegacySymantecAnchor=").append(verifiedChainHasLegacySymantecAnchor);
        sb.append(", ocspResponseResponseStatus=").append(ocspResponseResponseStatus);
        sb.append(", ocspResponseCertificateStatus=").append(ocspResponseCertificateStatus);
        sb.append(", ocspResponseRevocationTime=").append(ocspResponseRevocationTime);
        sb.append(", ocspResponseProducedAt=").append(ocspResponseProducedAt);
        sb.append(", ocspResponseThisUpdate=").append(ocspResponseThisUpdate);
        sb.append(", ocspResponseNextUpdate=").append(ocspResponseNextUpdate);
        sb.append(", ocspResponseSerialNumber=").append(ocspResponseSerialNumber);
        sb.append(", ocspResponseIsTrusted=").append(ocspResponseIsTrusted);
        sb.append(", index=").append(certificateDeploymentIndex);
        sb.append("]");
        return sb.toString();
    }
}