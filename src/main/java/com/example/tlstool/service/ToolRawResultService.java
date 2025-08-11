package com.example.tlstool.service;

import com.example.tlstool.entity.po.ToolRawResultPO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;

/**
* @author admin
* @description 针对表【tool_raw_result】的数据库操作Service
* @createDate 2025-08-03 18:37:45
*/
public interface ToolRawResultService extends IService<ToolRawResultPO> {
    void saveRawResultFromSslyze(JsonNode jsonRoot, Long targetId);
}
