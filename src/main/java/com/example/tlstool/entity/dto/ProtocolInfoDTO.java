package com.example.tlstool.entity.dto;

public class ProtocolInfoDTO {
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
