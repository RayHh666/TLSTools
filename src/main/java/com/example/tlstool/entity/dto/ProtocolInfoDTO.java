package com.example.tlstool.entity.dto;

import lombok.Data;

@Data
public class ProtocolInfoDTO {
    /**
     * targetId
     */
    String targetId;

    /**
     * ip
     */
    String ip;

    /**
     * 端口
     */
    int port;

    /**
     * tls加密协议版本
     */
    String tlsVersion;

    /**
     * 启用状态：0：未启用，1：启用
     */
    Integer accepted;
}
