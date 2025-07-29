package com.example.tlstool.service;

import com.example.tlstool.entity.po.TlsTaskPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;

/**
* @author admin
* @description 针对表【t_tls_task】的数据库操作Service
* @createDate 2025-07-28 18:32:18
*/
public interface TlsTaskService extends IService<TlsTaskPO> {

    Boolean createTask (TlsCreateTaskRO tlsCreateTaskRO) throws Exception;
}
