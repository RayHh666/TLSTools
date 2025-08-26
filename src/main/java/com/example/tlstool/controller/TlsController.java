package com.example.tlstool.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.tlstool.entity.dto.CertificateChainDTO;
import com.example.tlstool.entity.po.CertificateDetailPO;
import com.example.tlstool.entity.po.ScanTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.entity.vo.*;
import com.example.tlstool.service.*;
import com.example.tlstool.util.Resp;
import com.example.tlstool.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tls")
@Slf4j
public class TlsController {

    // TODO 上传目标列表文件创建任务

    @Resource
    private TlsTaskService tlsTaskService;

    @Resource
    private ScanTaskService scanTaskService;

    @Resource
    private ScanTargetService scanTargetService;

    @Resource
    private CertificateDetailService certificateDetailService;

    @Resource
    private CipherSuiteSupportService cipherSuiteSupportService;

    @Resource
    private VulnerabilityDetectionService vulnerabilityDetectionService;

    @Resource
    private HttpUpgradeService httpUpgradeService;

    @PostMapping("/create")
    public Result createTask(@RequestBody TlsCreateTaskRO tlsCreateTaskRO){
        try {
            Long taskId = scanTaskService.createTask(tlsCreateTaskRO);
            return Result.success(taskId);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping(value = "/stop")
    public Resp stop(Long taskId) {
        try {
            JSONObject response = scanTaskService.stopTask(taskId);
            if (response.containsKey("code") && 200 == (Integer) response.get("code")) {
                return Resp.getInstantiationSuccess("成功", null, null);
            } else {
                throw new Exception("调用停止任务接口失败！");
            }
        } catch (Exception e) {
            return Resp.getInstantiationError("失败" + e.getMessage(), null, null);
        }
    }

    @GetMapping(value = "/start")
    public Resp start(Long taskId) {
        try {
            JSONObject response = scanTaskService.startTask(taskId);
            if (response.containsKey("code") && 200 == (Integer) response.get("code")) {
                return Resp.getInstantiationSuccess("成功", null, null);
            } else {
                throw new Exception("调用开启任务接口失败！");
            }
        } catch (Exception e) {
            return Resp.getInstantiationError("失败" + e.getMessage(), null, null);
        }
    }

    @PostMapping(value = "/trigger")
    public Resp trigger(Long taskId) {
        try {
            JSONObject response = scanTaskService.triggerTask(taskId);
            if (response.containsKey("code") && 200 == (Integer) response.get("code")) {
                return Resp.getInstantiationSuccess("成功", null, null);
            } else {
                throw new Exception("调用xxl-job-admin-start接口失败！");
            }
        } catch (Exception e) {
            return Resp.getInstantiationError("失败" + e.getMessage(), null, null);
        }
    }

    @GetMapping(value = "/remove")
    public Resp remove(Long taskId) {
        try {
            JSONObject response = scanTaskService.removeTaskById(taskId);
            if (response.containsKey("code") && 200 == (Integer) response.get("code")) {
                return Resp.getInstantiationSuccess("成功", null, null);
            } else {
                throw new Exception("调用停止任务接口失败！");
            }
        } catch (Exception e) {
            return Resp.getInstantiationError("失败" + e.getMessage(), null, null);
        }
    }

    @GetMapping("/protocol_info_page")
    public Result<ProtocolInfoVO> getProtocolInfoPage(Long taskId,
                                                         Integer count,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        try {
            ProtocolInfoVO protocolInfoPage =  scanTargetService.getProtocolInfoPage(taskId, count, page, size);
            return Result.success(protocolInfoPage);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/cipher_suite_info_page")
    public Result<CipherSuiteInfoVO> getCipherSuiteInfoPage(Long taskId, Long targetId, String tlsVersion, Integer count,
                                                            @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        try {
            CipherSuiteInfoVO cipherSuiteInfoPage = cipherSuiteSupportService.getCipherSuiteInfoPage(taskId, targetId, tlsVersion, count, page, size);
            return Result.success(cipherSuiteInfoPage);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/certificate_info_page")
    public Result<CertificateInfoVO> getCertificateInfoPage(Long taskId, Integer count,
                                                            @RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        try {
            CertificateInfoVO certificateInfoPage = certificateDetailService.getCertificateInfoPage(taskId, count, page, size);
            return Result.success(certificateInfoPage);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/received_certificate_chain")
    public Result<CertificateChainDTO> getReceivedCertificateChain(Long certificateDeploymentId) {
        try {
            CertificateChainDTO certificateChainDetailDTOList = certificateDetailService.getCertificateChainDetail(certificateDeploymentId);
            return Result.success(certificateChainDetailDTOList);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/vulnerability_detection_result")
    public Result<VulnerabilityDetectionResultVO> getVulnerabilityDetectionResult(Long taskId, Integer count,
                                                                                  @RequestParam(defaultValue = "1") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        try {
            VulnerabilityDetectionResultVO VulnerabilityDetectionResultList = vulnerabilityDetectionService.getVulnerabilityDetectionResultList(taskId, count, page, size);
            return Result.success(VulnerabilityDetectionResultList);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/task_info")
    public Result<ScanTaskPO> getTaskInfo(Long taskId) {
        try {
            ScanTaskPO scanTaskPO = scanTaskService.getTaskInfoById(taskId);
            return Result.success(scanTaskPO);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    //  查询http重定向结果
    @GetMapping("/http_redirection")
    public Result<HttpUpgradeResultVO> getHttpRedirectionResult(Long taskId, Integer count,
                                                                @RequestParam(defaultValue = "1") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        try {
            HttpUpgradeResultVO httpUpgradeResultVO = httpUpgradeService.getHttpRedirectionResult(taskId, count, page, size);
            return Result.success(httpUpgradeResultVO);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    // 查询证书链

    // TODO Resolved [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: "null"]

    // TODO  More than one TaskExecutor bean found within the context, and none is named 'taskExecutor'. Mark one of them as primary or name it 'taskExecutor' (possibly as an alias) in order to use it for async processing: [mainTaskExecutor, subTaskExecutor]
}
