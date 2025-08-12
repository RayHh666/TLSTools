package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.tlstool.Executor.SslyzeExecutor;
import com.example.tlstool.entity.po.ScanTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.mapper.ScanTaskMapper;
import com.example.tlstool.service.*;
import com.example.tlstool.util.DateTimeUtils;
import com.example.tlstool.util.SslyzeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    @Resource
    private ScanTargetService scanTargetService;

    @Resource
    private ConnectionDetailService connectionDetailService;

    @Resource
    private CertificateDeploymentService certificateDeploymentService;

    @Resource
    private CipherSuiteSupportService cipherSuiteSupportService;

    @Resource
    private VulnerabilityDetectionService vulnerabilityDetectionService;

    @Resource
    private ToolRawResultService toolRawResultService;

    @Resource
    private ScanTaskMapper scanTaskMapper;

    @Qualifier("mainTaskExecutor")
    @Resource
    private Executor mainTaskExecutor;

    @Qualifier("subTaskExecutor")
    @Resource
    private Executor subTaskExecutor;

    @Async("mainTaskExecutor")
    @Override
    public void createSslyzeTask (TlsCreateTaskRO tlsCreateTaskRO, Long taskId) throws Exception {
        String sslyzeCommand = "";
        // 扫描目标
        Set<String> targetSet = new HashSet<>(Arrays.asList(tlsCreateTaskRO.getTargets().split(",")));
        // String targetsStr = tlsCreateTaskRO.getTargets().trim().replace(",", " ");

        // TLS扫描参数
        if (StringUtils.isNotBlank(tlsCreateTaskRO.getTlsProtocols())) {
            Set<String> tlsProtocolSet = new HashSet<>(Arrays.asList(tlsCreateTaskRO.getTlsProtocols().split(",")));
            for (String tlsProtocol: tlsProtocolSet) {
                sslyzeCommand = sslyzeCommand + " --" + tlsProtocol;
            }
        }

        // STARTTLS扫描参数
        if (StringUtils.isNotBlank(tlsCreateTaskRO.getStarttlsMailProtocol())) {
            sslyzeCommand = sslyzeCommand + " --starttls=" + tlsCreateTaskRO.getStarttlsMailProtocol();
        }

        // 漏洞扫描参数
        sslyzeCommand = sslyzeCommand + " --heartbleed --robot --compression --elliptic_curves --openssl_ccs --certinfo";

        final String finalSslyzeCommand = sslyzeCommand;

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (String target : targetSet) {
            futures.add(CompletableFuture.runAsync(() ->
                    {
                        try {
                            singleSslyzeScan(finalSslyzeCommand, target, taskId);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    },
                    subTaskExecutor // 专用子任务线程池
            ));
        }





        // 所有子任务完成后更新主任务状态
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
//                .thenRunAsync(() -> updateMainTaskStatus(taskId), mainTaskExecutor);

    }

//    public void updateMainTaskStatus(Long taskId) {
//    }

    @Async
    public void singleSslyzeScan(String sslyzeCommand, String target, Long taskId) throws Exception {
        // 解析本次扫描结果集
        String sslyzeVersion = null;
        String sslyzeUrl = null;
        LocalDateTime dateScansCompleted = null;
        JsonNode jsonRoot = SslyzeUtils.getSslyzeJsonOutput(sslyzeCommand, target);
        if (jsonRoot != null) {
            if (jsonRoot.findValue("server_scan_results") != null && jsonRoot.findValue("server_scan_results").isArray()) {
                JsonNode serverScanResults = jsonRoot.findValue("server_scan_results");
                for (JsonNode serverScanResult : serverScanResults) {
                    if (serverScanResult.findValue("server_location") != null && !serverScanResult.findValue("server_location").isNull()) {
                        Long targetId = scanTargetService.saveSslyzeTargetInfo(serverScanResult.findValue("server_location"), taskId);
                        // 存连接信息
                        if (serverScanResult.findValue("connectivity_result") != null && !serverScanResult.findValue("connectivity_result").isNull()) {
                            log.info("connectivity_result: {}", serverScanResult.findValue("connectivity_result"));
                            connectionDetailService.saveConnectionDetail(serverScanResult.findValue("connectivity_result"), targetId);
                        }
                        // 存certificateDeploymentService
                        if (serverScanResult.findValue("certificate_deployments") != null && !serverScanResult.findValue("certificate_deployments").isNull()) {
                            // log.info("certificate_deployments: {}", serverScanResult.findValue("certificate_deployments"));
                            certificateDeploymentService.saveCertificateDeployment(serverScanResult.findValue("certificate_deployments"), targetId);
                        }
                        // 存密钥套件
                        if (serverScanResult.findValue("ssl_2_0_cipher_suites") != null && !serverScanResult.findValue("ssl_2_0_cipher_suites").isNull()) {
                            // log.info("ssl_2_0_cipher_suites: {}", serverScanResult.findValue("ssl_2_0_cipher_suites"));
                            cipherSuiteSupportService.saveCipherSuiteSupportInfo(serverScanResult.findValue("ssl_2_0_cipher_suites"), targetId);
                        }
                        if (serverScanResult.findValue("ssl_3_0_cipher_suites") != null && !serverScanResult.findValue("ssl_3_0_cipher_suites").isNull()) {
                            // log.info("ssl_3_0_cipher_suites: {}", serverScanResult.findValue("ssl_3_0_cipher_suites"));
                            cipherSuiteSupportService.saveCipherSuiteSupportInfo(serverScanResult.findValue("ssl_3_0_cipher_suites"), targetId);
                        }
                        if (serverScanResult.findValue("tls_1_0_cipher_suites") != null && !serverScanResult.findValue("tls_1_0_cipher_suites").isNull()) {
                            // log.info("tls_1_0_cipher_suites: {}", serverScanResult.findValue("tls_1_0_cipher_suites"));
                            cipherSuiteSupportService.saveCipherSuiteSupportInfo(serverScanResult.findValue("tls_1_0_cipher_suites"), targetId);
                        }
                        if (serverScanResult.findValue("tls_1_1_cipher_suites") != null && !serverScanResult.findValue("tls_1_1_cipher_suites").isNull()) {
                            // log.info("tls_1_1_cipher_suites: {]", serverScanResult.findValue("tls_1_1_cipher_suites"));
                            cipherSuiteSupportService.saveCipherSuiteSupportInfo(serverScanResult.findValue("tls_1_1_cipher_suites"), targetId);
                        }
                        if (serverScanResult.findValue("tls_1_2_cipher_suites") != null && !serverScanResult.findValue("tls_1_2_cipher_suites").isNull()) {
                            // log.info("tls_1_2_cipher_suites: {}", serverScanResult.findValue("tls_1_2_cipher_suites"));
                            cipherSuiteSupportService.saveCipherSuiteSupportInfo(serverScanResult.findValue("tls_1_2_cipher_suites"), targetId);
                        }
                        if (serverScanResult.findValue("tls_1_3_cipher_suites") != null && !serverScanResult.findValue("tls_1_3_cipher_suites").isNull()) {
                            // log.info("tls_1_3_cipher_suites", serverScanResult.findValue("tls_1_3_cipher_suites"));
                            cipherSuiteSupportService.saveCipherSuiteSupportInfo(serverScanResult.findValue("tls_1_3_cipher_suites"), targetId);
                        }
                        // 存漏洞信息
                        if (serverScanResult.findValue("openssl_ccs_injection") != null && !serverScanResult.findValue("openssl_ccs_injection").isNull()) {
                            log.info("openssl_ccs_injection", serverScanResult.findValue("openssl_ccs_injection"));
                            vulnerabilityDetectionService.saveCommonVulnerabilityInfoFromSslyze(serverScanResult.findValue("openssl_ccs_injection"), targetId, "openssl_ccs_injection", "CVE-2014-0224");
                        }
                        if (serverScanResult.findValue("heartbleed") != null && !serverScanResult.findValue("heartbleed").isNull()) {
                            log.info("heartbleed: {}", serverScanResult.findValue("heartbleed"));
                            vulnerabilityDetectionService.saveCommonVulnerabilityInfoFromSslyze(serverScanResult.findValue("heartbleed"), targetId, "heartbleed", "CVE-2014-0160");
                        }
                        if (serverScanResult.findValue("robot") != null && !serverScanResult.findValue("robot").isNull()) {
                            log.info("robot: {}", serverScanResult.findValue("robot"));
                            vulnerabilityDetectionService.saveCommonVulnerabilityInfoFromSslyze(serverScanResult.findValue("robot"), targetId, "robot", "CVE-2017-13098");
                        }
                        if (serverScanResult.findValue("session_renegotiation") != null && !serverScanResult.findValue("session_renegotiation").isNull()) {
                            log.info("session_renegotiation: {}", serverScanResult.findValue("session_renegotiation"));
                            vulnerabilityDetectionService.saveCommonVulnerabilityInfoFromSslyze(serverScanResult.findValue("session_renegotiation"), targetId, "session_renegotiation", "CVE-2009-3555");
                        }

                        // 存原始数据
                        toolRawResultService.saveRawResultFromSslyze(jsonRoot, targetId);


                    }
                }
            }

            sslyzeVersion = jsonRoot.findValue("sslyze_version").asText();
            sslyzeUrl = jsonRoot.findValue("sslyze_url").asText();
            dateScansCompleted = DateTimeUtils.convertToLocalDateTime(jsonRoot.findValue("date_scans_completed").asText());

            // 更新扫描任务信息
            LambdaUpdateWrapper<ScanTaskPO> updateWrapper = new LambdaUpdateWrapper<ScanTaskPO>()
                    .eq(ScanTaskPO::getTaskId, taskId)
                    .set(ScanTaskPO::getToolName, "sslyze")
                    .set(ScanTaskPO::getToolVersion, sslyzeVersion)
                    .set(ScanTaskPO::getStatus, "COMPLETED")
                    .set(ScanTaskPO::getRemark, sslyzeUrl)
                    .set(ScanTaskPO::getCompletedAt, dateScansCompleted);
            scanTaskMapper.update(null, updateWrapper);
        }
    }
}
