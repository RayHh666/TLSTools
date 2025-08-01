package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.TlsTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.service.ScanToolService.SslyzeScanResultService;
import com.example.tlstool.service.TlsTaskService;
import com.example.tlstool.mapper.TlsTaskMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
* @author admin
* @description 针对表【t_tls_task】的数据库操作Service实现
* @createDate 2025-07-28 18:32:18
*/
@Service
public class TlsTaskServiceImpl extends ServiceImpl<TlsTaskMapper, TlsTaskPO>
    implements TlsTaskService {

    @Resource
    private SslyzeScanResultService sslyzeScanResultService;

    @Override
    public void createTask(TlsCreateTaskRO tlsCreateTaskRO) throws Exception{

        TlsTaskPO tlsTaskPO = new TlsTaskPO().builder()
                .taskName(tlsCreateTaskRO.getTaskName())
                .targets(tlsCreateTaskRO.getTargets())
                .detectionTool(tlsCreateTaskRO.getDetectionTool())
                .tlsProtocols(tlsCreateTaskRO.getTlsProtocols())
                .mailProtocols(tlsCreateTaskRO.getMailProtocols())
                .isHttpSecurityHeaderDetection(tlsCreateTaskRO.getIsHttpSecurityHeaderDetection())
                .progress(0)
                .state(2)
                .createTime(LocalDateTime.now())
                .build();
        baseMapper.insert(tlsTaskPO);
        sslyzeScanResultService.createSslyzeTask(tlsCreateTaskRO, tlsTaskPO);
    }
}




