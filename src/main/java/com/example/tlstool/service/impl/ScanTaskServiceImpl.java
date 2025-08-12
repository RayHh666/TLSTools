package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.ScanTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.service.*;
import com.example.tlstool.mapper.ScanTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
* @author admin
* @description 针对表【scan_task】的数据库操作Service实现
* @createDate 2025-08-03 18:37:45
*/
@Service
@Slf4j
public class ScanTaskServiceImpl extends ServiceImpl<ScanTaskMapper, ScanTaskPO>
    implements ScanTaskService{

    @Resource
    private AsyncService asyncService;

    @Override
    public Long createTask(TlsCreateTaskRO tlsCreateTaskRO) throws Exception {
            log.info("target:{}", tlsCreateTaskRO.getTargets());
            ScanTaskPO scanTaskPO = new ScanTaskPO().builder()
                    .taskName(tlsCreateTaskRO.getTaskName() + LocalDateTime.now())
                    .targets(tlsCreateTaskRO.getTargets())
                    .taskType(tlsCreateTaskRO.getTaskType())
                    .createdAt(LocalDateTime.now())
                    .build();
            baseMapper.insert(scanTaskPO);
            switch (tlsCreateTaskRO.getTaskType()) {
                case "TLS_SCAN" :
                    asyncService.createSslyzeTask(tlsCreateTaskRO, scanTaskPO.getTaskId());
                    break;
                case "STARTTLS_SCAN" :
                    asyncService.createSslyzeTask(tlsCreateTaskRO, scanTaskPO.getTaskId());
                    break;
                // TODO http重定向
                case "HTTP_SCAN" :
                    createCurlTask(tlsCreateTaskRO, scanTaskPO.getTaskId(), "-IL", 5);
            }
        return scanTaskPO.getTaskId();
    }

    @Async
    // TODO curl检查http重定向
    public void createCurlTask(TlsCreateTaskRO tlsCreateTaskRO, Long taskId, String command, Integer maxTimes) {
        String curlCommand = "curl";
        if (StringUtils.isNotBlank(command)) {
            curlCommand = curlCommand + " " + command;
            if (maxTimes != null) {
                curlCommand = curlCommand + " --max-time" + " " + maxTimes;
            }
        }

        Set<String> targetSet = new HashSet<>(Arrays.asList(tlsCreateTaskRO.getTargets()));
        for (String target : targetSet) {
            if (StringUtils.isNotBlank(target)) {
                try {
                    curlCommand = curlCommand + " " + target;
                    ProcessBuilder builder = new ProcessBuilder();
                    String osName = System.getProperty("os.name").toLowerCase();
                    if (osName.contains("windows")) {
                        // windowssystem
                        builder.command("cmd", "/c", curlCommand);
                    } else {
                        // Other systems
                        builder.command("bash", "-c", curlCommand);
                    }
                    builder.redirectErrorStream(true);
                    Process process = builder.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        LambdaUpdateWrapper<ScanTaskPO> updateWrapper = new LambdaUpdateWrapper<ScanTaskPO>()
                .eq(ScanTaskPO::getTaskId, taskId)
                .set(ScanTaskPO::getToolName, "curl")
                .set(ScanTaskPO::getCompletedAt, LocalDateTime.now());
        baseMapper.update(null, updateWrapper);
    }
}




