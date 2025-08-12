package com.example.tlstool.controller;

import com.example.tlstool.entity.dto.CertificateInfoDTO;
import com.example.tlstool.entity.dto.CipherSuiteInfoDTO;
import com.example.tlstool.entity.dto.ProtocolInfoDTO;
import com.example.tlstool.entity.dto.VulnerabilityDetectionResultDTO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
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
    public Result<List<ProtocolInfoDTO>> getProtocolInfoPage(Long taskId,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        try {
            List<ProtocolInfoDTO> protocolInfoPage =  scanTargetService.getProtocolInfoPage(taskId, page, size);
            return Result.success(protocolInfoPage);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/cipher_suite_info_page")
    public Result<List<CipherSuiteInfoDTO>> getCipherSuiteInfoPage(Long taskId,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        try {
            List<CipherSuiteInfoDTO> cipherSuiteInfoPage = cipherSuiteSupportService.getCipherSuiteInfoPage(taskId, page, size);
            return Result.success(cipherSuiteInfoPage);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/certificate_info_page")
    public Result<List<CertificateInfoDTO>> getCertificateInfoPage(Long taskId,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        try {
            List<CertificateInfoDTO> certificateInfoPage = certificateDetailService.getCertificateInfoPage(taskId, page, size);
            return Result.success(certificateInfoPage);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/vulnerability_detection_result")
    public Result<List<VulnerabilityDetectionResultDTO>> getVulnerabilityDetectionResult(Long taskId) {
        try {
            List<VulnerabilityDetectionResultDTO> VulnerabilityDetectionResultList = vulnerabilityDetectionService.getVulnerabilityDetectionResultList(taskId);
            return Result.success(VulnerabilityDetectionResultList);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }
}
