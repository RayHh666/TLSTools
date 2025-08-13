package com.example.tlstool.service;

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
    Long createTask (TlsCreateTaskRO tlsCreateTaskRO) throws Exception;

    ScanTaskPO getTaskInfoById(Long taskId);
}
