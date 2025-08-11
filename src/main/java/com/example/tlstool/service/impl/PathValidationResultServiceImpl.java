package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.PathValidationResultPO;
import com.example.tlstool.service.CertificateDetailService;
import com.example.tlstool.service.PathValidationResultService;
import com.example.tlstool.mapper.PathValidationResultMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author admin
* @description 针对表【path_validation_result】的数据库操作Service实现
* @createDate 2025-08-06 17:08:57
*/
@Service
public class PathValidationResultServiceImpl extends ServiceImpl<PathValidationResultMapper, PathValidationResultPO>
    implements PathValidationResultService{

    @Resource
    private CertificateDetailService certificateDetailService;

    public void savePahtValidationResultFromSslyze(JsonNode pathValidationResults, Long certificateDeploymentId) {
        if (pathValidationResults != null && pathValidationResults.isArray()) {
            Integer index = 0;
            for (JsonNode pathValidationResult : pathValidationResults) {
                JsonNode trustStore = pathValidationResult.get("trust_store");
                if (trustStore != null && !trustStore.isNull()) {
                    String path = null;
                    String name = null;
                    String version = null;
                    String evOids = null;
                    if (trustStore.get("path") != null && !trustStore.get("path").isNull()) {
                        path = trustStore.get("path").asText();
                    }
                    if (trustStore.get("name") != null && !trustStore.get("name").isNull()) {
                        name = trustStore.get("name").asText();
                    }
                    if (trustStore.get("version") != null && !trustStore.get("version").isNull()) {
                        version = trustStore.get("version").asText();
                    }
                    if (trustStore.get("evOids") != null && !trustStore.get("evOids").isNull()) {
                        evOids = trustStore.get("evOids").asText();
                    }
                    PathValidationResultPO pathValidationResultPO = new PathValidationResultPO().builder()
                            .trustStorePath(path)
                            .trustStoreName(name)
                            .trustStoreVersion(version)
                            .trustStoreEvOids(evOids)
                            .validationError(pathValidationResult.findValue("validation_error").asText())
                            .wasValidationSuccessful(pathValidationResult.findValue("was_validation_successful").asBoolean() ? 1 : 0)
                            .pathValidationResultIndex(index)
                            .certificateDeploymentId(certificateDeploymentId)
                            .build();
                    baseMapper.insert(pathValidationResultPO);
                    certificateDetailService.saveCertificateDetailFromSslyze(pathValidationResult.findValue("verified_certificate_chain"), null, pathValidationResultPO.getPathValidationResultId());
                }
                index++;
            }
        }
    }
}




