package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.dto.ProtocolInfoDTO;
import com.example.tlstool.entity.po.ScanTargetPO;
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
    public Long createTarget (Long taskId, String target) {
        ScanTargetPO scanTargetPO = new ScanTargetPO().builder()
                .taskId(taskId)
                .target(target)
                .build();
        scanTargetMapper.insert(scanTargetPO);
        return scanTargetPO.getTargetId();
    }

    @Override
    public void  updateSslyzeTargetInfo (JsonNode serverLocation, Long targetId) {


        ScanTargetPO scanTargetPO = new ScanTargetPO();
        scanTargetPO.setProxySettings(serverLocation.findValue("http_proxy_settings"));

        LambdaUpdateWrapper<ScanTargetPO> updateWrapper = new LambdaUpdateWrapper<ScanTargetPO>()
                .eq(ScanTargetPO::getTargetId, targetId)
                .set(ScanTargetPO::getHost, serverLocation.get("hostname").asText())
                .set(ScanTargetPO::getPort, serverLocation.get("port").asInt())
                .set(ScanTargetPO::getResolvedIp, serverLocation.get("ip_address").asText())
                .set(ScanTargetPO::getConnectionType, serverLocation.get("connection_type").asText());
        baseMapper.update(scanTargetPO, updateWrapper);
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
    public List<ProtocolInfoDTO> getProtocolInfoPage (Long taskId, int page, int size){
        int offset = (page - 1) * size;
        return baseMapper.getProtocolInfoPageByTaskId(taskId, offset, size);
    }
}




