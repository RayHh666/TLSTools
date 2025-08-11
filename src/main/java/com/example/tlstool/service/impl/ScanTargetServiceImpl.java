package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.ScanTargetPO;
import com.example.tlstool.entity.po.ScanToolPO.SslyzeScanResultPO;
import com.example.tlstool.entity.po.TlsTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.service.ScanTargetService;
import com.example.tlstool.mapper.ScanTargetMapper;
import com.example.tlstool.util.DateTimeUtils;
import com.example.tlstool.util.SslyzeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
* @author admin
* @description 针对表【scan_target】的数据库操作Service实现
* @createDate 2025-08-03 18:37:45
*/
@Service
public class ScanTargetServiceImpl extends ServiceImpl<ScanTargetMapper, ScanTargetPO>
    implements ScanTargetService{

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
}




