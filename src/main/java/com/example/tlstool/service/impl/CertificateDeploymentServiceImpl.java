package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.CertificateDeploymentPO;
import com.example.tlstool.service.CertificateDeploymentService;
import com.example.tlstool.mapper.CertificateDeploymentMapper;
import com.example.tlstool.service.CertificateDetailService;
import com.example.tlstool.service.PathValidationResultService;
import com.example.tlstool.util.DateTimeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

/**
* @author admin
* @description 针对表【certificate_deployment】的数据库操作Service实现
* @createDate 2025-08-06 17:08:56
*/
@Service
public class CertificateDeploymentServiceImpl extends ServiceImpl<CertificateDeploymentMapper, CertificateDeploymentPO>
    implements CertificateDeploymentService{

    @Resource
    private CertificateDetailService certificateDetailService;

    @Resource
    private PathValidationResultService pathValidationResultService;

    @Async
    @Override
    public void saveCertificateDeployment(JsonNode certificateDeployments, Long targetId) {
        if(certificateDeployments != null) {
            if (certificateDeployments.isArray()) {
                Integer index = 0;
                for (JsonNode certificateDeployment : certificateDeployments) {
                    String ocspResponseResponseStatus = null;
                    String ocspResponseCertificateStatus = null;
                    LocalDateTime ocspResponseRevocationTime = null;
                    LocalDateTime ocspResponseProducedAt = null;
                    LocalDateTime ocspResponseThisUpdate = null;
                    LocalDateTime ocspResponseNextUpdate = null;
                    Long ocspResponseSerialNumber =null;
                    Integer ocspResponseIsTrusted = null;

                    JsonNode ocspResponse = certificateDeployment.findValue("ocsp_response");
                    if (ocspResponse != null && !ocspResponse.isNull()) {
                        if (ocspResponse.findValue("response_status") != null && !ocspResponse.findValue("response_status").isNull()) {
                            ocspResponseResponseStatus = ocspResponse.findValue("response_status").asText();
                        }
                        if (ocspResponse.findValue("certificate_status") != null && !ocspResponse.findValue("certificate_status").isNull()) {
                            ocspResponseCertificateStatus = ocspResponse.findValue("certificate_status").asText();
                        }
                        if (ocspResponse.findValue("revocation_time") != null && !ocspResponse.findValue("revocation_time").isNull()) {
                            ocspResponseRevocationTime = DateTimeUtils.convertToLocalDateTime(ocspResponse.findValue("revocation_time").asText());
                        }
                        if (ocspResponse.findValue("produced_at") != null && !ocspResponse.findValue("produced_at").isNull()) {
                            ocspResponseProducedAt = DateTimeUtils.convertToLocalDateTime(ocspResponse.findValue("produced_at").asText());
                        }
                        if (ocspResponse.findValue("this_update") != null && !ocspResponse.findValue("this_update").isNull()) {
                            ocspResponseThisUpdate = DateTimeUtils.convertToLocalDateTime(ocspResponse.findValue("this_update").asText());
                        }
                        if (ocspResponse.findValue("next_update") != null && !ocspResponse.findValue("next_update").isNull()) {
                            ocspResponseNextUpdate = DateTimeUtils.convertToLocalDateTime(ocspResponse.findValue("next_update").asText());
                        }
                        if (ocspResponse.findValue("serial_number") != null && !ocspResponse.findValue("serial_number").isNull()) {
                            ocspResponseSerialNumber = ocspResponse.findValue("serial_number").asLong();
                        }
                        if (certificateDeployment.findValue("ocsp_response_is_trusted") != null && !certificateDeployment.findValue("ocsp_response_is_trusted").isNull()) {
                            ocspResponseIsTrusted = certificateDeployment.findValue("ocsp_response_is_trusted").asBoolean() ? 1 : 0;
                        }

                    }

                    CertificateDeploymentPO certificateDeploymentPO = new CertificateDeploymentPO().builder()
                            .targetId(targetId)
                            .leafCertificateHasMustStapleExtension(certificateDeployment.findValue("leaf_certificate_has_must_staple_extension").asBoolean() ? 1 : 0)
                            .leafCertificateIsEv((certificateDeployment.findValue("leaf_certificate_is_ev").asBoolean() ? 1 : 0))
                            .leafCertificateSignedCertificateTimestampsCount(certificateDeployment.findValue("leaf_certificate_signed_certificate_timestamps_count").asInt())
                            .receivedChainContainsAnchorCertificate((certificateDeployment.findValue("received_chain_contains_anchor_certificate").asBoolean() ? 1 : 0))
                            .receivedChainHasValidOrder(certificateDeployment.findValue("received_chain_has_valid_order").asBoolean() ? 1 : 0)
                            .verifiedChainHasSha1Signature(certificateDeployment.findValue("verified_chain_has_sha1_signature").asBoolean() ? 1 : 0)
                            .verifiedChainHasLegacySymantecAnchor(certificateDeployment.findValue("verified_chain_has_legacy_symantec_anchor").asBoolean() ? 1 : 0)
                            .ocspResponseResponseStatus(ocspResponseResponseStatus)
                            .ocspResponseCertificateStatus(ocspResponseCertificateStatus)
                            .ocspResponseRevocationTime(ocspResponseRevocationTime)
                            .ocspResponseProducedAt(ocspResponseProducedAt)
                            .ocspResponseThisUpdate(ocspResponseThisUpdate)
                            .ocspResponseNextUpdate(ocspResponseNextUpdate)
                            .ocspResponseSerialNumber(ocspResponseSerialNumber)
                            .ocspResponseIsTrusted(ocspResponseIsTrusted)
                            .certificateDeploymentIndex(index)
                            .build();
                    baseMapper.insert(certificateDeploymentPO);

                    certificateDetailService.saveCertificateDetailFromSslyze(certificateDeployment.findValue("received_certificate_chain"), certificateDeploymentPO.getCertificateDeploymentId(), null);
                    pathValidationResultService.savePahtValidationResultFromSslyze(certificateDeployment.findValue("path_validation_results"), certificateDeploymentPO.getCertificateDeploymentId());
                    index++;
                }
            }
        }
    }
}




