package com.example.tlstool.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CipherSuiteInfoDTO {
    /**
     * 密钥名称
     */
    String cipherName;

    /**
     * 目标是否支持
     */
    Integer accepted;

    /**
     * 密钥长度
     */
    Integer keySize;

    /**
     * 是否支持前向加密 0：不支持，1：支持
     */
    Integer forwardSecrecy;

    /**
     * openssl名称
     */
    String opensslName;

}
