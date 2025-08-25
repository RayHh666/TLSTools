package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.dto.ProtocolInfoDTO;
import com.example.tlstool.entity.po.ScanTargetPO;
import com.example.tlstool.entity.vo.ProtocolInfoVO;
import com.example.tlstool.service.ScanTargetService;
import com.example.tlstool.mapper.ScanTargetMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author admin
* @description 针对表【scan_target】的数据库操作Service实现
* @createDate 2025-08-03 18:37:45
*/
@Service
public class ScanTargetServiceImpl extends ServiceImpl<ScanTargetMapper, ScanTargetPO>
    implements ScanTargetService{

    private final ScanTargetMapper scanTargetMapper;

    public ScanTargetServiceImpl(ScanTargetMapper scanTargetMapper) {
        this.scanTargetMapper = scanTargetMapper;
    }

    @Override
    public Long createTarget (Long taskId, String target, int count) {
        ScanTargetPO scanTargetPO = new ScanTargetPO().builder()
                .taskId(taskId)
                .target(target)
                .count(count)
                .build();
        scanTargetMapper.insert(scanTargetPO);
        return scanTargetPO.getTargetId();
    }

    @Override
    public void  updateSslyzeTargetInfo (JsonNode serverLocation, Long targetId) {
        ScanTargetPO scanTargetPO = baseMapper.selectById(targetId);
        scanTargetPO.setProxySettings(serverLocation.findValue("http_proxy_settings"));
        scanTargetPO.setHost(serverLocation.get("hostname").asText());
        scanTargetPO.setPort(serverLocation.get("port").asInt());
        scanTargetPO.setResolvedIp(serverLocation.get("ip_address").asText());
        scanTargetPO.setConnectionType(serverLocation.get("connection_type").asText());
        baseMapper.updateById(scanTargetPO);
    }

    @Override
    public Long saveSslyzeTargetInfo (JsonNode serverLocation, Long taskId) {
        ScanTargetPO scanTargetPO = new ScanTargetPO().builder()
                .taskId(taskId)
                .host(serverLocation.get("hostname").asText())
                .port(serverLocation.get("port").asInt())
                .resolvedIp(serverLocation.get("ip_address").asText())
                .connectionType(serverLocation.get("connection_type").asText())
                .proxySettings(serverLocation.findValue("http_proxy_settings"))
                .build();
        baseMapper.insert(scanTargetPO);
        return scanTargetPO.getTargetId();
    }

    @Override
    public ProtocolInfoVO getProtocolInfoPage (Long taskId, Integer count, int page, int size){
        ProtocolInfoVO protocolInfoVO = new ProtocolInfoVO();
        int offset = (page - 1) * size;
        List<ProtocolInfoDTO> protocolInfoDTOList = baseMapper.getProtocolInfoPageByTaskId(taskId, count, offset, size);
        Integer total = baseMapper.getProtocolInfoTotalByTaskId(taskId, count);

        protocolInfoVO.setTotal(total);
        protocolInfoVO.setProtocolInfoDTOList(protocolInfoDTOList);
        return protocolInfoVO;
    }
}




