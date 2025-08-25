package com.example.tlstool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tlstool.entity.po.HttpUpgradeResultPO;
import com.example.tlstool.entity.vo.HttpUpgradeResultVO;

public interface HttpUpgradeService extends IService<HttpUpgradeResultPO> {
    HttpUpgradeResultVO getHttpRedirectionResult(Long taskId, Integer count, int page, int size);
}
