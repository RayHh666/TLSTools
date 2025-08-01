package com.example.tlstool.service.ScanToolService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tlstool.entity.po.ScanToolPO.SslyzeScanResultPO;
import com.example.tlstool.entity.po.TlsTaskPO;
import com.example.tlstool.entity.ro.TlsCreateTaskRO;

/**
* @author admin
* @description 针对表【t_sslyze_scan_result】的数据库操作Service
* @createDate 2025-07-30 16:32:47
*/
public interface SslyzeScanResultService extends IService<SslyzeScanResultPO> {
    void createSslyzeTask (TlsCreateTaskRO tlsCreateTaskRO, TlsTaskPO tlsTaskPO) throws Exception;
}
