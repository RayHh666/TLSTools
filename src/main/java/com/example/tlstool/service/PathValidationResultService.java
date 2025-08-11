package com.example.tlstool.service;

import com.example.tlstool.entity.po.PathValidationResultPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;

/**
* @author admin
* @description 针对表【path_validation_result】的数据库操作Service
* @createDate 2025-08-06 17:08:57
*/
public interface PathValidationResultService extends IService<PathValidationResultPO> {
    void savePahtValidationResultFromSslyze(JsonNode pathValidationResults, Long certificateDeploymentId);
}
