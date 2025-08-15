package com.example.tlstool.controller;

import com.example.tlstool.entity.dto.CertificateChainDetailDTO;
import com.example.tlstool.entity.dto.VulnerabilityDetectionResultDTO;
import com.example.tlstool.entity.po.CertificateDetailPO;
import com.example.tlstool.entity.po.ScanTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.entity.vo.CertificateInfoVO;
import com.example.tlstool.entity.vo.CipherSuiteInfoVO;
import com.example.tlstool.entity.vo.ProtocolInfoVO;
import com.example.tlstool.entity.vo.VulnerabilityDetectionResultVO;
import com.example.tlstool.service.*;
import com.example.tlstool.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tls")
@Slf4j
public class TlsController {

    // TODO
    //  1、通过目标列表创建任务
    //  2、上传目标列表文件创建任务

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

//    @PostMapping("/create")
//    public Result createTask(@RequestBody TlsCreateTaskRO tlsCreateTaskRO){
//        try {
//            tlsTaskService.createTask(tlsCreateTaskRO);
//        } catch (Exception e) {
//            return Result.error(e.getMessage());
//        }
//        return Result.success();// tlsTaskService.
//    }

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

    @GetMapping("/protocol_info_page")
    public Result<ProtocolInfoVO> getProtocolInfoPage(Long taskId,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        try {
            ProtocolInfoVO protocolInfoPage =  scanTargetService.getProtocolInfoPage(taskId, page, size);
            return Result.success(protocolInfoPage);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/cipher_suite_info_page")
    public Result<CipherSuiteInfoVO> getCipherSuiteInfoPage(Long taskId, Long targetId, String tlsVersion,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        try {
            CipherSuiteInfoVO cipherSuiteInfoPage = cipherSuiteSupportService.getCipherSuiteInfoPage(taskId, targetId, tlsVersion, page, size);
            return Result.success(cipherSuiteInfoPage);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/certificate_info_page")
    public Result<CertificateInfoVO> getCertificateInfoPage(Long taskId,
                                                            @RequestParam(defaultValue = "1") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        try {
            CertificateInfoVO certificateInfoPage = certificateDetailService.getCertificateInfoPage(taskId, page, size);
            return Result.success(certificateInfoPage);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/received_certificate_chain")
    public Result<List<CertificateDetailPO>> getReceivedCertificateChain(Long certificateDeploymentId) {
        try {
            List<CertificateDetailPO> certificateChainDetailDTOList = certificateDetailService.getCertificateChainDetail(certificateDeploymentId);
            return Result.success(certificateChainDetailDTOList);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/vulnerability_detection_result")
    public Result<VulnerabilityDetectionResultVO> getVulnerabilityDetectionResult(Long taskId,
                                                                                  @RequestParam(defaultValue = "1") int page,
                                                                                  @RequestParam(defaultValue = "10") int size) {
        try {
            VulnerabilityDetectionResultVO VulnerabilityDetectionResultList = vulnerabilityDetectionService.getVulnerabilityDetectionResultList(taskId, page, size);
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

    // TODO 查询http重定向结果

    // 查询证书链

    // TODO Resolved [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: "null"]

    // TODO 重构任务结束逻辑，将“COMPLETED”字段放在target中，最后更新task状态

    // TODO  More than one TaskExecutor bean found within the context, and none is named 'taskExecutor'. Mark one of them as primary or name it 'taskExecutor' (possibly as an alias) in order to use it for async processing: [mainTaskExecutor, subTaskExecutor]
}
