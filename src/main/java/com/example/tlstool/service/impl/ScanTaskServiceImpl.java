package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.ScanTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.service.*;
import com.example.tlstool.mapper.ScanTaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
        asyncService.createAsyncTask(tlsCreateTaskRO, scanTaskPO.getTaskId());
        return scanTaskPO.getTaskId();
    }

    @Override
    public ScanTaskPO getTaskInfoById(Long taskId) {
        return baseMapper.selectById(taskId);
    }
}



