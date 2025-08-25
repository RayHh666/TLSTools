package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.dto.CipherSuiteInfoDTO;
import com.example.tlstool.entity.dto.ProtocolInfoDTO;
import com.example.tlstool.entity.po.CipherSuiteSupportPO;
import com.example.tlstool.entity.vo.CipherSuiteInfoVO;
import com.example.tlstool.entity.vo.ProtocolInfoVO;
import com.example.tlstool.service.CipherSuiteSupportService;
import com.example.tlstool.mapper.CipherSuiteSupportMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author admin
* @description 针对表【cipher_suite_support】的数据库操作Service实现
* @createDate 2025-08-03 18:37:45
*/
@Service
public class CipherSuiteSupportServiceImpl extends ServiceImpl<CipherSuiteSupportMapper, CipherSuiteSupportPO>
    implements CipherSuiteSupportService{

    @Override
    public void saveCipherSuiteSupportInfo(JsonNode cipherSuiteSupportInfo, Long targetId) {
        if (cipherSuiteSupportInfo.findValue("result") != null && !cipherSuiteSupportInfo.findValue("result").isNull()) {
            String tlsVersion = cipherSuiteSupportInfo.findValue("tls_version_used").asText();
            JsonNode acceptedCipherSuites = cipherSuiteSupportInfo.findValue("accepted_cipher_suites");

            if (acceptedCipherSuites.isArray()) {
                for (JsonNode acceptedCipherSuite : acceptedCipherSuites) {

                    String cipherName = acceptedCipherSuite.findValue("name").asText();
                    Integer forwardSecrecy = null;
                    if (cipherName.contains("DHE")){
                        forwardSecrecy = 1;
                    } else {
                        forwardSecrecy = 0;
                    }

                    CipherSuiteSupportPO cipherSuiteSupportPO = new CipherSuiteSupportPO().builder()
                            .targetId(targetId)
                            .tlsVersion(tlsVersion)
                            .cipherName(cipherName)
                            .isAccepted(1)
                            .keySize(acceptedCipherSuite.findValue("key_size").asInt())
                            .forwardSecrecy(forwardSecrecy)
//                        .rating()
                            .opensslName(acceptedCipherSuite.findValue("openssl_name").asText())
                            .build();
                    baseMapper.insert(cipherSuiteSupportPO);
                }
            }
        }
    }

    @Override
    public CipherSuiteInfoVO getCipherSuiteInfoPage(Long taskId, Long targetId, String tlsVersion, Integer count, int page, int size) {
        CipherSuiteInfoVO cipherSuiteInfoVO = new CipherSuiteInfoVO();
        int offset = (page - 1) * size;
        List<CipherSuiteInfoDTO> cipherSuiteInfoDTOList = baseMapper.getCipherSuiteInfoPageByTaskId(taskId, targetId, tlsVersion, count, offset, size);
        Integer total = baseMapper.getCipherSuiteInfoTotalByTaskId(taskId, targetId, tlsVersion, count);
        cipherSuiteInfoVO.setTotal(total);
        cipherSuiteInfoVO.setCipherSuiteInfoDTOList(cipherSuiteInfoDTOList);
        return cipherSuiteInfoVO;
    }
}




