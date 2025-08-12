package com.example.tlstool.service;

import com.example.tlstool.entity.dto.CertificateInfoDTO;
import com.example.tlstool.entity.po.CertificateDetailPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
* @author admin
* @description 针对表【certificate_detail】的数据库操作Service
* @createDate 2025-08-06 17:08:57
*/
public interface CertificateDetailService extends IService<CertificateDetailPO> {
    void saveCertificateDetailFromSslyze(JsonNode certificateDetails, Long certificateDeploymentId, Long pathValidationResultId);

    List<CertificateInfoDTO> getCertificateInfoPage(Long taskId, int page, int size);
}
