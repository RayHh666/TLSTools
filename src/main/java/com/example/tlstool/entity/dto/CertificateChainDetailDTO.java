package com.example.tlstool.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CertificateChainDetailDTO {
    private Long certId;

    private String asPem;

    /**
     * 'SHA1指纹'
     */
    private String fingerprintSha1;

    /**
     * SHA256指纹 (唯一标识)
     */
    private String fingerprintSha256;

    /**
     * '证书序列号'
     */
    private String serialNumber;
}
