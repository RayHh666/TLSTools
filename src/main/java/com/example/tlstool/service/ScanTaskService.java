package com.example.tlstool.service;

import com.alibaba.fastjson.JSONObject;
import com.example.tlstool.entity.po.ScanTaskPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tlstool.entity.po.TlsTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;

import java.util.Set;

/**
* @author admin
* @description 针对表【scan_task】的数据库操作Service
* @createDate 2025-08-03 18:37:45
*/
public interface ScanTaskService extends IService<ScanTaskPO> {
    /**
     * 创建并启动任务
     *
     * @param tlsCreateTaskRO
     * @return
     * @throws Exception
     */
    Long createTask(TlsCreateTaskRO tlsCreateTaskRO) throws Exception;

    /**
     * 获取任务信息
     *
     * @param taskId
     * @return
     */
    ScanTaskPO getTaskInfoById(Long taskId);

    /**
     * 暂停任务
     *
     * @param taskId
     * @return
     */
    JSONObject stopTask(Long taskId);

    /**
     * 单次触发任务
     *
     * @param taskId
     * @return
     */
    JSONObject triggerTask(Long taskId);

    /**
     * 删除任务
     *
     * @param taskId
     * @return
     */
    JSONObject removeTaskById(Long taskId);
}
