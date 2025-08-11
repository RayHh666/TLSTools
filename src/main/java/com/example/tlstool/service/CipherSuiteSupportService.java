package com.example.tlstool.service;

import com.example.tlstool.entity.po.CipherSuiteSupportPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;

/**
* @author admin
* @description 针对表【cipher_suite_support】的数据库操作Service
* @createDate 2025-08-03 18:37:45
*/
public interface CipherSuiteSupportService extends IService<CipherSuiteSupportPO> {
    void saveCipherSuiteSupportInfo(JsonNode cipherSuiteSupportInfo, Long targetId);
}
