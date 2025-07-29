package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.TlsTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.service.TlsTaskService;
import com.example.tlstool.mapper.TlsTaskMapper;
import com.example.tlstool.util.SslyzeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
* @author admin
* @description 针对表【t_tls_task】的数据库操作Service实现
* @createDate 2025-07-28 18:32:18
*/
@Service
public class TlsTaskServiceImpl extends ServiceImpl<TlsTaskMapper, TlsTaskPO>
    implements TlsTaskService {

    @Override
    public Boolean createTask(TlsCreateTaskRO tlsCreateTaskRO) throws Exception{
        String sslyzeCommand;
        // TODO 将前端参数转换为sslyze command
        Set<String> targets = new HashSet<>(Arrays.asList(tlsCreateTaskRO.getTarget().split(",")));
        Set<String> tlsProtocols = new HashSet<>(Arrays.asList(tlsCreateTaskRO.getTlsProtocol().split(",")));
        Set<String> mailProtocols = new HashSet<>(Arrays.asList(tlsCreateTaskRO.getMailProtocol().split(",")));

        sslyzeCommand = "--sslv2";
        JsonNode jsonRoot = SslyzeUtils.parseJsonOutput("localhost", sslyzeCommand);

        return true;
    }
}




