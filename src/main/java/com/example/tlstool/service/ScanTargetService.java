package com.example.tlstool.service;

import com.example.tlstool.entity.dto.ProtocolInfoDTO;
import com.example.tlstool.entity.po.ScanTargetPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

/**
* @author admin
* @description 针对表【scan_target】的数据库操作Service
* @createDate 2025-08-03 18:37:45
*/
public interface ScanTargetService extends IService<ScanTargetPO> {
    Long saveSslyzeTargetInfo (JsonNode serverLocation, Long taskId);

    List<ProtocolInfoDTO> getProtocolInfoPage (Long taskId, int page, int size);
}
