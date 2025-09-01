package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.ConnectionDetailPO;
import com.example.tlstool.service.ConnectionDetailService;
import com.example.tlstool.mapper.ConnectionDetailMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【connection_detail】的数据库操作Service实现
* @createDate 2025-08-03 18:37:45
*/
@Service
public class ConnectionDetailServiceImpl extends ServiceImpl<ConnectionDetailMapper, ConnectionDetailPO>
    implements ConnectionDetailService{

    @Async
    @Override
    public void saveConnectionDetail(JsonNode connectivityResult, Long targetId) {
        if (connectivityResult != null && !connectivityResult.isNull()) {
            ConnectionDetailPO connectionDetailPO = new ConnectionDetailPO().builder()
                    .targetId(targetId)
                    .highestTlsVersion(connectivityResult.get("highest_tls_version_supported").asText())
                    .cipherSuiteSupported(connectivityResult.get("cipher_suite_supported").asText())
                    .clientAuthRequirement(connectivityResult.get("client_auth_requirement").asText())
                    .supportsEcdhKeyExchange(convertToTinyInt(connectivityResult.get("supports_ecdh_key_exchange").asText()))
                    .build();
            baseMapper.insert(connectionDetailPO);
        }
    }

    private Integer convertToTinyInt(String input) {
        if(input == null) return 0; // 处理空值
        Boolean boolValue = Boolean.parseBoolean(input);
        return boolValue ? 1 : 0;
    }


}




