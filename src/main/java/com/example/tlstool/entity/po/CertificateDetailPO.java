package com.example.tlstool.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

import com.example.tlstool.handler.JsonNodeTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName certificate_detail
 */
@TableName(value ="certificate_detail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDetailPO {
    /**
     * 
     */
    @TableId(value = "cert_id", type = IdType.AUTO)
    private Long certId;

    /**
     * 证书PEM格式
     */
    @TableField(value = "as_pem")
    private String asPem;

    /**
     * SHA1指纹
     */
    @TableField(value = "fingerprint_sha1")
    private String fingerprintSha1;

    /**
     * SHA256指纹 (唯一标识)
     */
    @TableField(value = "fingerprint_sha256")
    private String fingerprintSha256;

    /**
     * 证书序列号
     */
    @TableField(value = "serial_number")
    private String serialNumber;

    /**
     * 有效期开始
     */
    @TableField(value = "not_valid_before")
    private LocalDateTime notValidBefore;

    /**
     * 有效期结束
     */
    @TableField(value = "not_valid_after")
    private LocalDateTime notValidAfter;

    /**
     * 签名算法
     */
    @TableField(value = "signature_hash_algorithm_name")
    private String signatureHashAlgorithmName;

    /**
     * 签名算法中哈希函数的输出长度
     */
    @TableField(value = "signature_hash_algorithm_digest_size")
    private Integer signatureHashAlgorithmDigestSize;

    /**
     * 签名算法OID
     */
    @TableField(value = "signature_algorithm_oid_name")
    private String signatureAlgorithmOidName;

    /**
     * 签名算法的精确标识
     */
    @TableField(value = "signature_algorithm_oid_dotted_string")
    private String signatureAlgorithmOidDottedString;

    /**
     * 主题完整DN
     */
    @TableField(value = "subject_rfc4514_string")
    private String subjectRfc4514String;

    /**
     * 主题国家
     */
    @TableField(value = "subject_country")
    private String subjectCountry;

    /**
     * 主题州/省
     */
    @TableField(value = "subject_state")
    private String subjectState;

    /**
     * 主题地区
     */
    @TableField(value = "subject_locality")
    private String subjectLocality;

    /**
     * 主题组织
     */
    @TableField(value = "subject_organization")
    private String subjectOrganization;

    /**
     * 主题组织单元
     */
    @TableField(value = "subject_organizational_unit")
    private String subjectOrganizationalUnit;

    /**
     * 主题通用名
     */
    @TableField(value = "subject_common_name")
    private String subjectCommonName;

    /**
     * 颁发者完整DN
     */
    @TableField(value = "issuer_rfc4514_string")
    private String issuerRfc4514String;

    /**
     * 颁发者国家
     */
    @TableField(value = "issuer_country")
    private String issuerCountry;

    /**
     * 颁发者州/省
     */
    @TableField(value = "issuer_state")
    private String issuerState;

    /**
     * 颁发者地区
     */
    @TableField(value = "issuer_locality")
    private String issuerLocality;

    /**
     * 颁发者组织
     */
    @TableField(value = "issuer_organization")
    private String issuerOrganization;

    /**
     * 颁发者组织单元
     */
    @TableField(value = "issuer_organizational_unit")
    private String issuerOrganizationalUnit;

    /**
     * 颁发者通用名
     */
    @TableField(value = "issuer_common_name")
    private String issuerCommonName;

    /**
     * 公钥算法
     */
    @TableField(value = "public_key_algorithm")
    private String publicKeyAlgorithm;

    /**
     * 公钥大小 (位)
     */
    @TableField(value = "public_key_key_size")
    private Integer publicKeyKeySize;

    /**
     * 
     */
    @TableField(value = "public_key_rsa_e")
    private Long publicKeyRsaE;

    /**
     * 
     */
    @TableField(value = "public_key_rsa_n")
    private Long publicKeyRsaN;

    /**
     * 椭圆曲线名称 (ECC)
     */
    @TableField(value = "public_key_ec_curve_name")
    private String publicKeyEcCurveName;

    /**
     * 
     */
    @TableField(value = "public_key_ec_x")
    private Long publicKeyEcX;

    /**
     * 
     */
    @TableField(value = "public_key_ec_y")
    private Long publicKeyEcY;

    /**
     * 主题备用名称
     */
    @TableField(value = "subject_alternative_name", typeHandler = JsonNodeTypeHandler.class)
    private JsonNode subjectAlternativeName;

    /**
     * 
     */
    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    /**
     * 关联certificate_deployment_id，仅当存储数据为接收证书时填写
     */
    @TableField(value = "certificate_deployment_id")
    private Long certificateDeploymentId;

    /**
     * 关联path_validation_result_id，仅当存储数据为已验证证书时填写
     */
    @TableField(value = "path_validation_result_id")
    private Long pathValidationResultId;

    /**
     * 证书在证书链中的索引
     */
    @TableField(value = "certificate_detail_index")
    private Integer certificateDetailIndex;

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
        CertificateDetailPO other = (CertificateDetailPO) that;
        return (this.getCertId() == null ? other.getCertId() == null : this.getCertId().equals(other.getCertId()))
            && (this.getAsPem() == null ? other.getAsPem() == null : this.getAsPem().equals(other.getAsPem()))
            && (this.getFingerprintSha1() == null ? other.getFingerprintSha1() == null : this.getFingerprintSha1().equals(other.getFingerprintSha1()))
            && (this.getFingerprintSha256() == null ? other.getFingerprintSha256() == null : this.getFingerprintSha256().equals(other.getFingerprintSha256()))
            && (this.getSerialNumber() == null ? other.getSerialNumber() == null : this.getSerialNumber().equals(other.getSerialNumber()))
            && (this.getNotValidBefore() == null ? other.getNotValidBefore() == null : this.getNotValidBefore().equals(other.getNotValidBefore()))
            && (this.getNotValidAfter() == null ? other.getNotValidAfter() == null : this.getNotValidAfter().equals(other.getNotValidAfter()))
            && (this.getSignatureHashAlgorithmName() == null ? other.getSignatureHashAlgorithmName() == null : this.getSignatureHashAlgorithmName().equals(other.getSignatureHashAlgorithmName()))
            && (this.getSignatureHashAlgorithmDigestSize() == null ? other.getSignatureHashAlgorithmDigestSize() == null : this.getSignatureHashAlgorithmDigestSize().equals(other.getSignatureHashAlgorithmDigestSize()))
            && (this.getSignatureAlgorithmOidName() == null ? other.getSignatureAlgorithmOidName() == null : this.getSignatureAlgorithmOidName().equals(other.getSignatureAlgorithmOidName()))
            && (this.getSignatureAlgorithmOidDottedString() == null ? other.getSignatureAlgorithmOidDottedString() == null : this.getSignatureAlgorithmOidDottedString().equals(other.getSignatureAlgorithmOidDottedString()))
            && (this.getSubjectRfc4514String() == null ? other.getSubjectRfc4514String() == null : this.getSubjectRfc4514String().equals(other.getSubjectRfc4514String()))
            && (this.getSubjectCountry() == null ? other.getSubjectCountry() == null : this.getSubjectCountry().equals(other.getSubjectCountry()))
            && (this.getSubjectState() == null ? other.getSubjectState() == null : this.getSubjectState().equals(other.getSubjectState()))
            && (this.getSubjectLocality() == null ? other.getSubjectLocality() == null : this.getSubjectLocality().equals(other.getSubjectLocality()))
            && (this.getSubjectOrganization() == null ? other.getSubjectOrganization() == null : this.getSubjectOrganization().equals(other.getSubjectOrganization()))
            && (this.getSubjectOrganizationalUnit() == null ? other.getSubjectOrganizationalUnit() == null : this.getSubjectOrganizationalUnit().equals(other.getSubjectOrganizationalUnit()))
            && (this.getSubjectCommonName() == null ? other.getSubjectCommonName() == null : this.getSubjectCommonName().equals(other.getSubjectCommonName()))
            && (this.getIssuerRfc4514String() == null ? other.getIssuerRfc4514String() == null : this.getIssuerRfc4514String().equals(other.getIssuerRfc4514String()))
            && (this.getIssuerCountry() == null ? other.getIssuerCountry() == null : this.getIssuerCountry().equals(other.getIssuerCountry()))
            && (this.getIssuerState() == null ? other.getIssuerState() == null : this.getIssuerState().equals(other.getIssuerState()))
            && (this.getIssuerLocality() == null ? other.getIssuerLocality() == null : this.getIssuerLocality().equals(other.getIssuerLocality()))
            && (this.getIssuerOrganization() == null ? other.getIssuerOrganization() == null : this.getIssuerOrganization().equals(other.getIssuerOrganization()))
            && (this.getIssuerOrganizationalUnit() == null ? other.getIssuerOrganizationalUnit() == null : this.getIssuerOrganizationalUnit().equals(other.getIssuerOrganizationalUnit()))
            && (this.getIssuerCommonName() == null ? other.getIssuerCommonName() == null : this.getIssuerCommonName().equals(other.getIssuerCommonName()))
            && (this.getPublicKeyAlgorithm() == null ? other.getPublicKeyAlgorithm() == null : this.getPublicKeyAlgorithm().equals(other.getPublicKeyAlgorithm()))
            && (this.getPublicKeyKeySize() == null ? other.getPublicKeyKeySize() == null : this.getPublicKeyKeySize().equals(other.getPublicKeyKeySize()))
            && (this.getPublicKeyRsaE() == null ? other.getPublicKeyRsaE() == null : this.getPublicKeyRsaE().equals(other.getPublicKeyRsaE()))
            && (this.getPublicKeyRsaN() == null ? other.getPublicKeyRsaN() == null : this.getPublicKeyRsaN().equals(other.getPublicKeyRsaN()))
            && (this.getPublicKeyEcCurveName() == null ? other.getPublicKeyEcCurveName() == null : this.getPublicKeyEcCurveName().equals(other.getPublicKeyEcCurveName()))
            && (this.getPublicKeyEcX() == null ? other.getPublicKeyEcX() == null : this.getPublicKeyEcX().equals(other.getPublicKeyEcX()))
            && (this.getPublicKeyEcY() == null ? other.getPublicKeyEcY() == null : this.getPublicKeyEcY().equals(other.getPublicKeyEcY()))
            && (this.getSubjectAlternativeName() == null ? other.getSubjectAlternativeName() == null : this.getSubjectAlternativeName().equals(other.getSubjectAlternativeName()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getCertificateDeploymentId() == null ? other.getCertificateDeploymentId() == null : this.getCertificateDeploymentId().equals(other.getCertificateDeploymentId()))
            && (this.getPathValidationResultId() == null ? other.getPathValidationResultId() == null : this.getPathValidationResultId().equals(other.getPathValidationResultId()))
            && (this.getCertificateDetailIndex() == null ? other.getCertificateDetailIndex() == null : this.getCertificateDetailIndex().equals(other.getCertificateDetailIndex()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCertId() == null) ? 0 : getCertId().hashCode());
        result = prime * result + ((getAsPem() == null) ? 0 : getAsPem().hashCode());
        result = prime * result + ((getFingerprintSha1() == null) ? 0 : getFingerprintSha1().hashCode());
        result = prime * result + ((getFingerprintSha256() == null) ? 0 : getFingerprintSha256().hashCode());
        result = prime * result + ((getSerialNumber() == null) ? 0 : getSerialNumber().hashCode());
        result = prime * result + ((getNotValidBefore() == null) ? 0 : getNotValidBefore().hashCode());
        result = prime * result + ((getNotValidAfter() == null) ? 0 : getNotValidAfter().hashCode());
        result = prime * result + ((getSignatureHashAlgorithmName() == null) ? 0 : getSignatureHashAlgorithmName().hashCode());
        result = prime * result + ((getSignatureHashAlgorithmDigestSize() == null) ? 0 : getSignatureHashAlgorithmDigestSize().hashCode());
        result = prime * result + ((getSignatureAlgorithmOidName() == null) ? 0 : getSignatureAlgorithmOidName().hashCode());
        result = prime * result + ((getSignatureAlgorithmOidDottedString() == null) ? 0 : getSignatureAlgorithmOidDottedString().hashCode());
        result = prime * result + ((getSubjectRfc4514String() == null) ? 0 : getSubjectRfc4514String().hashCode());
        result = prime * result + ((getSubjectCountry() == null) ? 0 : getSubjectCountry().hashCode());
        result = prime * result + ((getSubjectState() == null) ? 0 : getSubjectState().hashCode());
        result = prime * result + ((getSubjectLocality() == null) ? 0 : getSubjectLocality().hashCode());
        result = prime * result + ((getSubjectOrganization() == null) ? 0 : getSubjectOrganization().hashCode());
        result = prime * result + ((getSubjectOrganizationalUnit() == null) ? 0 : getSubjectOrganizationalUnit().hashCode());
        result = prime * result + ((getSubjectCommonName() == null) ? 0 : getSubjectCommonName().hashCode());
        result = prime * result + ((getIssuerRfc4514String() == null) ? 0 : getIssuerRfc4514String().hashCode());
        result = prime * result + ((getIssuerCountry() == null) ? 0 : getIssuerCountry().hashCode());
        result = prime * result + ((getIssuerState() == null) ? 0 : getIssuerState().hashCode());
        result = prime * result + ((getIssuerLocality() == null) ? 0 : getIssuerLocality().hashCode());
        result = prime * result + ((getIssuerOrganization() == null) ? 0 : getIssuerOrganization().hashCode());
        result = prime * result + ((getIssuerOrganizationalUnit() == null) ? 0 : getIssuerOrganizationalUnit().hashCode());
        result = prime * result + ((getIssuerCommonName() == null) ? 0 : getIssuerCommonName().hashCode());
        result = prime * result + ((getPublicKeyAlgorithm() == null) ? 0 : getPublicKeyAlgorithm().hashCode());
        result = prime * result + ((getPublicKeyKeySize() == null) ? 0 : getPublicKeyKeySize().hashCode());
        result = prime * result + ((getPublicKeyRsaE() == null) ? 0 : getPublicKeyRsaE().hashCode());
        result = prime * result + ((getPublicKeyRsaN() == null) ? 0 : getPublicKeyRsaN().hashCode());
        result = prime * result + ((getPublicKeyEcCurveName() == null) ? 0 : getPublicKeyEcCurveName().hashCode());
        result = prime * result + ((getPublicKeyEcX() == null) ? 0 : getPublicKeyEcX().hashCode());
        result = prime * result + ((getPublicKeyEcY() == null) ? 0 : getPublicKeyEcY().hashCode());
        result = prime * result + ((getSubjectAlternativeName() == null) ? 0 : getSubjectAlternativeName().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getCertificateDeploymentId() == null) ? 0 : getCertificateDeploymentId().hashCode());
        result = prime * result + ((getPathValidationResultId() == null) ? 0 : getPathValidationResultId().hashCode());
        result = prime * result + ((getCertificateDetailIndex() == null) ? 0 : getCertificateDetailIndex().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", certId=").append(certId);
        sb.append(", asPem=").append(asPem);
        sb.append(", fingerprintSha1=").append(fingerprintSha1);
        sb.append(", fingerprintSha256=").append(fingerprintSha256);
        sb.append(", serialNumber=").append(serialNumber);
        sb.append(", notValidBefore=").append(notValidBefore);
        sb.append(", notValidAfter=").append(notValidAfter);
        sb.append(", signatureHashAlgorithmName=").append(signatureHashAlgorithmName);
        sb.append(", signatureHashAlgorithmDigestSize=").append(signatureHashAlgorithmDigestSize);
        sb.append(", signatureAlgorithmOidName=").append(signatureAlgorithmOidName);
        sb.append(", signatureAlgorithmOidDottedString=").append(signatureAlgorithmOidDottedString);
        sb.append(", subjectRfc4514String=").append(subjectRfc4514String);
        sb.append(", subjectCountry=").append(subjectCountry);
        sb.append(", subjectState=").append(subjectState);
        sb.append(", subjectLocality=").append(subjectLocality);
        sb.append(", subjectOrganization=").append(subjectOrganization);
        sb.append(", subjectOrganizationalUnit=").append(subjectOrganizationalUnit);
        sb.append(", subjectCommonName=").append(subjectCommonName);
        sb.append(", issuerRfc4514String=").append(issuerRfc4514String);
        sb.append(", issuerCountry=").append(issuerCountry);
        sb.append(", issuerState=").append(issuerState);
        sb.append(", issuerLocality=").append(issuerLocality);
        sb.append(", issuerOrganization=").append(issuerOrganization);
        sb.append(", issuerOrganizationalUnit=").append(issuerOrganizationalUnit);
        sb.append(", issuerCommonName=").append(issuerCommonName);
        sb.append(", publicKeyAlgorithm=").append(publicKeyAlgorithm);
        sb.append(", publicKeyKeySize=").append(publicKeyKeySize);
        sb.append(", publicKeyRsaE=").append(publicKeyRsaE);
        sb.append(", publicKeyRsaN=").append(publicKeyRsaN);
        sb.append(", publicKeyEcCurveName=").append(publicKeyEcCurveName);
        sb.append(", publicKeyEcX=").append(publicKeyEcX);
        sb.append(", publicKeyEcY=").append(publicKeyEcY);
        sb.append(", subjectAlternativeName=").append(subjectAlternativeName);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", certificateDeploymentId=").append(certificateDeploymentId);
        sb.append(", pathValidationResultId=").append(pathValidationResultId);
        sb.append(", index=").append(certificateDetailIndex);
        sb.append("]");
        return sb.toString();
    }
}