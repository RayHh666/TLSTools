package com.example.tlstool.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.example.tlstool.Executor.SslyzeExecutor;
import com.example.tlstool.entity.dto.HttpUpgradeResultDTO;
import com.example.tlstool.entity.dto.TlsCreateTaskDTO;
import com.example.tlstool.entity.po.HttpUpgradeResultPO;
import com.example.tlstool.entity.po.ScanTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.mapper.HttpUpgradeMapper;
import com.example.tlstool.mapper.ScanTaskMapper;
import com.example.tlstool.service.*;
import com.example.tlstool.util.DateTimeUtils;
import com.example.tlstool.util.HttpUpgradeChecker;
import com.example.tlstool.util.SslyzeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Component
public class AsyncServiceImpl{

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

    @Resource
    private HttpUpgradeMapper httpUpgradeMapper;

    @Qualifier("mainTaskExecutor")
    @Resource
    private Executor mainTaskExecutor;

    @Qualifier("subTaskExecutor")
    @Resource
    private Executor subTaskExecutor;

//    @Async("mainTaskExecutor")
//    @XxlJob("TlsTask")
//    public void createAsyncTask () throws Exception {
//        XxlJobHelper.log("=================start async task=================");
//
//        String param = XxlJobHelper.getJobParam();
//        log.info("param: {}", param);
//        TlsCreateTaskDTO tlsCreateTaskDTO = JSON.parseObject(param, TlsCreateTaskDTO.class);
//
//        // 获取当前任务已执行次数并更新
//        ScanTaskPO scanTaskPO = scanTaskMapper.selectOne(new LambdaQueryWrapper<ScanTaskPO>().eq(ScanTaskPO::getTaskId ,tlsCreateTaskDTO.getTaskId()));
//        int count = scanTaskPO.getCount() + 1;
//        scanTaskPO.setCount(count);
//        scanTaskPO.setStatus("PENDING");
//        scanTaskMapper.updateById(scanTaskPO);
//
//        String sslyzeCommand = "";
//        // 扫描目标
//        Set<String> targetSet = new HashSet<>(Arrays.asList(tlsCreateTaskDTO.getTargets().split(",")));
//        // String targetsStr = tlsCreateTaskRO.getTargets().trim().replace(",", " ");
//
//        // TLS扫描参数
//        if (StringUtils.isNotBlank(tlsCreateTaskDTO.getTlsProtocols())) {
//            Set<String> tlsProtocolSet = new HashSet<>(Arrays.asList(tlsCreateTaskDTO.getTlsProtocols().split(",")));
//            for (String tlsProtocol: tlsProtocolSet) {
//                sslyzeCommand = sslyzeCommand + " --" + tlsProtocol;
//            }
//        }
//
//        // STARTTLS扫描参数
//        if (StringUtils.isNotBlank(tlsCreateTaskDTO.getStarttlsMailProtocol())) {
//            sslyzeCommand = sslyzeCommand + " --starttls=" + tlsCreateTaskDTO.getStarttlsMailProtocol();
//        }
//
//        // 漏洞扫描参数
//        sslyzeCommand = sslyzeCommand + " --heartbleed --reneg --robot --compression --elliptic_curves --openssl_ccs --certinfo";
//
//        final String finalSslyzeCommand = sslyzeCommand;
//
//        List<CompletableFuture<Void>> futures = new ArrayList<>();
//
//        for (String target : targetSet) {
//            futures.add(CompletableFuture.runAsync(() ->
//                    {
//                        Long targetId = scanTargetService.createTarget(tlsCreateTaskDTO.getTaskId(), target, count);
//                        try {
//                            if (tlsCreateTaskDTO.getTaskType().contains("STARTTLS_SCAN") || tlsCreateTaskDTO.getTaskType().contains("TLS_SCAN")) {
//                                singleSslyzeScan(finalSslyzeCommand, target, targetId, tlsCreateTaskDTO.getTaskId());
//                            }
//
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//
//                        try {
//                            if (tlsCreateTaskDTO.getTaskType().contains("HTTP_SCAN")) {
//                                createCurlTask(target, targetId ,tlsCreateTaskDTO.getTaskId());
//                            }
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    },
//                    subTaskExecutor // 专用子任务线程池
//            ));
//        }
//        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
//                futures.toArray(new CompletableFuture[0])
//        );
//        allFutures.join();
//        scanTaskPO.setStatus("COMPLETED");
//        scanTaskMapper.updateById(scanTaskPO);
//    }

    @XxlJob("TlsTask")
    public void createAsyncTask() throws Exception {
        XxlJobHelper.log("=================start async task=================");
        String param = XxlJobHelper.getJobParam();
        log.info("param: {}", param);
        TlsCreateTaskDTO tlsCreateTaskDTO = JSON.parseObject(param, TlsCreateTaskDTO.class);

        // 更新任务状态
        ScanTaskPO scanTaskPO = scanTaskMapper.selectOne(
                new LambdaQueryWrapper<ScanTaskPO>().eq(ScanTaskPO::getTaskId, tlsCreateTaskDTO.getTaskId())
        );
        int count = scanTaskPO.getCount() + 1;
        scanTaskPO.setCount(count);
        scanTaskPO.setStatus("PENDING");
        scanTaskMapper.updateById(scanTaskPO);

        // 构建SSLyze命令
        StringBuilder sslyzeCommand = new StringBuilder();
        if (StringUtils.isNotBlank(tlsCreateTaskDTO.getTlsProtocols())) {
            Arrays.stream(tlsCreateTaskDTO.getTlsProtocols().split(","))
                    .forEach(protocol -> sslyzeCommand.append(" --").append(protocol));
        }
        if (StringUtils.isNotBlank(tlsCreateTaskDTO.getStarttlsMailProtocol())) {
            sslyzeCommand.append(" --starttls=").append(tlsCreateTaskDTO.getStarttlsMailProtocol());
        }
        sslyzeCommand.append(" --heartbleed --reneg --robot --compression --elliptic_curves --openssl_ccs --certinfo");
        final String finalSslyzeCommand = sslyzeCommand.toString();

        // 创建线程池（根据任务量动态配置）
        int corePoolSize = Runtime.getRuntime().availableProcessors();
        int maxPoolSize = corePoolSize * 2;
        ThreadPoolExecutor subTaskExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy() // 避免任务丢失
        );
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        try {
            // 提交所有子任务
            Set<String> targetSet = new HashSet<>(Arrays.asList(tlsCreateTaskDTO.getTargets().split(",")));

            for (String target : targetSet) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    Long targetId = scanTargetService.createTarget(tlsCreateTaskDTO.getTaskId(), target, count);
                    try {
                        if (tlsCreateTaskDTO.getTaskType().contains("STARTTLS_SCAN") ||
                                tlsCreateTaskDTO.getTaskType().contains("TLS_SCAN")) {
                            singleSslyzeScan(finalSslyzeCommand, target, targetId, tlsCreateTaskDTO.getTaskId());
                        }
                        if (tlsCreateTaskDTO.getTaskType().contains("HTTP_SCAN")) {
                            createCurlTask(target, targetId, tlsCreateTaskDTO.getTaskId());
                        }
                    } catch (Exception e) {
                        log.error("子任务执行失败: target={}, error={}", target, e.getMessage());
                        throw new CompletionException(e); // 传播异常
                    }
                }, subTaskExecutor).exceptionally(ex -> {
                    log.error("子任务异常: target={}, error={}", target, ex.getMessage());
                    return null;
                });
                futures.add(future);
            }

            // 等待所有任务完成（带超时控制）
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allFutures.get(30, TimeUnit.MINUTES); // 设置合理超时避免永久阻塞[7,11](@ref)
            // 主任务完成后更新task状态
            scanTaskPO.setStatus("COMPLETED");
        } catch (TimeoutException e) {
            log.error("任务执行超时", e);
            scanTaskPO.setStatus("FAILED");
            // 取消未完成的任务
            futures.forEach(f -> f.cancel(true));
        } catch (Exception e) {
            log.error("任务执行异常", e);
            scanTaskPO.setStatus("FAILED");
        } finally {
            // 确保线程池关闭[9,10,11](@ref)
            shutdownExecutor(subTaskExecutor);
            scanTaskMapper.updateById(scanTaskPO);
        }
    }

    // 安全关闭线程池
    private void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow(); // 强制终止残留任务
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void singleSslyzeScan(String sslyzeCommand, String target, Long targetId, Long taskId) throws Exception {
        // 解析本次扫描结果集
        String sslyzeVersion = null;
        String sslyzeUrl = null;
        LocalDateTime dateScansCompleted = null;
        // TODO 任务异常处理
        JsonNode jsonRoot = SslyzeUtils.getSslyzeJsonOutput(sslyzeCommand, target);
        if (jsonRoot != null) {
            if (jsonRoot.findValue("server_scan_results") != null && jsonRoot.findValue("server_scan_results").isArray()) {
                JsonNode serverScanResults = jsonRoot.findValue("server_scan_results");
                for (JsonNode serverScanResult : serverScanResults) {
                    if (serverScanResult.findValue("server_location") != null && !serverScanResult.findValue("server_location").isNull()) {
                        log.info("server_location：{}", serverScanResult.findValue("server_location"));
                        scanTargetService.updateSslyzeTargetInfo(serverScanResult.findValue("server_location"), targetId);
                        // 连接失败，则直接跳过
                        if (serverScanResult.findValue("connectivity_status") != null && "ERROR".equals(serverScanResult.findValue("connectivity_status"))) {
                            log.info(serverScanResult.findValue("connectivity_status").asText());
                        } else {
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
            }

            sslyzeVersion = jsonRoot.findValue("sslyze_version").asText();
            sslyzeUrl = jsonRoot.findValue("sslyze_url").asText();
            dateScansCompleted = DateTimeUtils.convertToLocalDateTime(jsonRoot.findValue("date_scans_completed").asText());

            // 更新扫描任务信息
            LambdaUpdateWrapper<ScanTaskPO> updateWrapper = new LambdaUpdateWrapper<ScanTaskPO>()
                    .eq(ScanTaskPO::getTaskId, taskId)
                    .set(ScanTaskPO::getToolName, "sslyze")
                    .set(ScanTaskPO::getToolVersion, sslyzeVersion)
            //        .set(ScanTaskPO::getStatus, "COMPLETED")
                    .set(ScanTaskPO::getRemark, sslyzeUrl)
                    .set(ScanTaskPO::getCompletedAt, dateScansCompleted);
            scanTaskMapper.update(null, updateWrapper);
        }
    }

    public void createCurlTask(String target, Long targetId ,Long taskId) {
        HttpUpgradeResultPO httpUpgradeResultPO = new HttpUpgradeResultPO().builder()
                .targetId(targetId)
                .target(target)
                .build();
        try {
            HttpUpgradeResultDTO httpUpgradeResultDTO = HttpUpgradeChecker.isUpgradedToHttps(target);
            httpUpgradeResultPO.setStatus(httpUpgradeResultDTO.getStatus());
            httpUpgradeResultPO.setLocation(httpUpgradeResultDTO.getLocation());
            httpUpgradeMapper.insert(httpUpgradeResultPO);
        } catch (Exception e) {
            httpUpgradeResultPO.setErrorMessage(e.getMessage());
            httpUpgradeMapper.insert(httpUpgradeResultPO);
        }

        LambdaUpdateWrapper<ScanTaskPO> updateWrapper = new LambdaUpdateWrapper<ScanTaskPO>()
                .eq(ScanTaskPO::getTaskId, taskId)
                .set(ScanTaskPO::getToolName, "curl")
                .set(ScanTaskPO::getCompletedAt, LocalDateTime.now());
        scanTaskMapper.update(null, updateWrapper);
    }
}
