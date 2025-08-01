package com.example.tlstool.entity.ro;

import lombok.Data;

@Data
public class TlsCreateTaskRO {

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 检测工具 1-sslyze 2-sslscan 3-testssl
     */
    private Integer detectionTool;

    /**
     * 扫描目标 多个目标以逗号分隔
     */
    private String targets;

    /**
     * TLS协议 多个协议以逗号分割
     */
    private String tlsProtocols;

    /**
     * 邮件协议 多个协议以逗号分割
     */
    private String mailProtocols;

    /**
     * 是否开启HTTP安全头协议检测 0-否 1-是
     */
    private Integer isHttpSecurityHeaderDetection;

    /**
     * 所属版本
     */
    private String version;

    /**
     * 是否创建报告
     */
    private Integer isCreateReport;

}
