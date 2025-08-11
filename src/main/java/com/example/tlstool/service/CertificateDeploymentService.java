package com.example.tlstool.service;

import com.example.tlstool.entity.po.CertificateDeploymentPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;

/**
* @author admin
* @description 针对表【certificate_deployment】的数据库操作Service
* @createDate 2025-08-06 17:08:57
*/
public interface CertificateDeploymentService extends IService<CertificateDeploymentPO> {
    void saveCertificateDeployment(JsonNode certificateDeployments, Long targetId);
}
