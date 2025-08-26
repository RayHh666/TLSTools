package com.example.tlstool.service;

import com.example.tlstool.entity.dto.CertificateChainDTO;
import com.example.tlstool.entity.po.CertificateDetailPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tlstool.entity.vo.CertificateInfoVO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
* @author admin
* @description 针对表【certificate_detail】的数据库操作Service
* @createDate 2025-08-06 17:08:57
*/
public interface CertificateDetailService extends IService<CertificateDetailPO> {
    void saveCertificateDetailFromSslyze(JsonNode certificateDetails, Long certificateDeploymentId, Long pathValidationResultId);

    CertificateInfoVO getCertificateInfoPage(Long taskId,  Integer count, int page, int size);

    CertificateChainDTO getCertificateChainDetail(Long certificateDeploymentId);
}
