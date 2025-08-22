package com.example.tlstool.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.dto.TlsCreateTaskDTO;
import com.example.tlstool.entity.po.ScanTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import com.example.tlstool.service.*;
import com.example.tlstool.mapper.ScanTaskMapper;
import com.example.tlstool.util.XxlJob.XxlJobServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
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

//    @Resource
//    private AsyncService asyncService;

    @Resource
    private XxlJobServiceApi xxlJobServiceApi;

    @Value("${xxl.job.tlsconfig.handler}")
    private String handler;

    @Override
    public Long createTask(TlsCreateTaskRO tlsCreateTaskRO) throws Exception {
        int xxlJobId = 0;
        log.info("target:{}", tlsCreateTaskRO.getTargets());
        // TODO 失败后回滚
        ScanTaskPO scanTaskPO = new ScanTaskPO().builder()
                .taskName(tlsCreateTaskRO.getTaskName() + LocalDateTime.now())
                .xxlJobId(xxlJobId)
                .targets(tlsCreateTaskRO.getTargets())
                .taskType(tlsCreateTaskRO.getTaskType())
                .createdAt(LocalDateTime.now())
                .cron(tlsCreateTaskRO.getCron())
                .build();
        baseMapper.insert(scanTaskPO);
        // 重构为 创建xxl任务，xxl建定时任务
        try {
            TlsCreateTaskDTO tlsCreateTaskDTO = new TlsCreateTaskDTO();
            String jobDesc = "扫描类型：" + tlsCreateTaskRO.getTaskType() + "扫描目标：" + tlsCreateTaskRO.getTargets();
            String cron = tlsCreateTaskRO.getCron();
            // 构建xxl-job任务参数
            BeanUtils.copyProperties(tlsCreateTaskDTO, tlsCreateTaskRO);
            tlsCreateTaskDTO.setTaskId(scanTaskPO.getTaskId());
            String param = JSON.toJSONString(tlsCreateTaskDTO);
            // 创建xxl-job任务
            JSONObject jsonObject = xxlJobServiceApi.addXxlJob(jobDesc, cron, handler, param);
            if (jsonObject != null && jsonObject.getIntValue("code") == 200) {
                xxlJobId = jsonObject.getIntValue("content");
                scanTaskPO.setXxlJobId(xxlJobId);
                scanTaskPO.setParam(param);
                baseMapper.updateById(scanTaskPO);
                // 启动任务
                xxlJobServiceApi.startXxlJob(String.valueOf(xxlJobId));
                if ("INSTANT".equals(tlsCreateTaskRO.getExecType())) {
                    // 立即执行任务，cron表达式须为无法定时执行的表达式
                    xxlJobServiceApi.triggerXxlJob(String.valueOf(xxlJobId), param);
                }
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            scanTaskPO.setStatus("FAILED");
            baseMapper.updateById(scanTaskPO);
            throw e;
        }
        System.out.println("结束定时任务");
        return scanTaskPO.getTaskId();
    }

    @Override
    public ScanTaskPO getTaskInfoById(Long taskId) {
        return baseMapper.selectById(taskId);
    }

    @Override
    public JSONObject stopTask(Long taskId) {
        // TODO 查询xxl-job_id
        ScanTaskPO scanTaskPO = baseMapper.selectOne(new LambdaQueryWrapper<ScanTaskPO>().eq(ScanTaskPO::getTaskId, taskId).select(ScanTaskPO::getXxlJobId));
        int xxlJobId = scanTaskPO.getXxlJobId();
        //      调用xxl-job stop()
        return xxlJobServiceApi.stopXxlJob(String.valueOf(xxlJobId));
    }

    @Override
    public JSONObject triggerTask(Long taskId) {
        // TODO 查询xxl-job_id
        ScanTaskPO scanTaskPO = baseMapper.selectOne(new LambdaQueryWrapper<ScanTaskPO>().eq(ScanTaskPO::getTaskId, taskId));
        int xxlJobId = scanTaskPO.getXxlJobId();
        //      调用xxl-job trigger()
        return xxlJobServiceApi.triggerXxlJob(String.valueOf(xxlJobId), scanTaskPO.getParam());
    }
}



