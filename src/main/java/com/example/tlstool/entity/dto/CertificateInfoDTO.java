package com.example.tlstool.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CertificateInfoDTO {

    /**
     * 证书信息id
     */
    Long certId;

    /**
     * 证书序列号
     */
    String serialNumber;

    /**
     * 证书颁发单位
     */
    String issuerEfc4514String;

    /**
     * 有效期开始
     */
    LocalDateTime notValidBefore;

    /**
     * 有效期结束
     */
    LocalDateTime notValidAfter;

    /**
     * 公钥算法
     */
    String publicKeyAlgorithm;

    /**
     * 公钥大小
     */
    Integer publicKeySize;

    /**
     * 签名算法
     */
    String signatureHashAlgorithmName;

    /**
     * 是否验证成功
     */
    Integer wasValidationSuccessful;

    /**
     * 信任库路径
     */
    String trustStorePath;

    /**
     * 信任库名称
     */
    String trustStoreName;

    /**
     * 信任库版本
     */
    String trustStoreVersion;

    /**
     * ocsp响应是否被信任
     */
    String ocsp_response_is_trusted;

    /**
     * ocsp响应状态
     */
    String ocsp_response_response_status;

    /**
     * 验证链索引
     */
    Integer certificateDetailIndex;

    /**
     * 验证链id
     */
    Long pathValidationResultId;
}
