package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.ToolRawResultPO;
import com.example.tlstool.service.ToolRawResultService;
import com.example.tlstool.mapper.ToolRawResultMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
* @author admin
* @description 针对表【tool_raw_result】的数据库操作Service实现
* @createDate 2025-08-03 18:37:45
*/
@Service
public class ToolRawResultServiceImpl extends ServiceImpl<ToolRawResultMapper, ToolRawResultPO>
    implements ToolRawResultService{

    @Async
    @Override
    public void saveRawResultFromSslyze(JsonNode jsonRoot, Long targetId) {
        ToolRawResultPO toolRawResultPO = new ToolRawResultPO().builder()
                .targetId(targetId)
                .toolName("sslyze")
                .toolVersion(jsonRoot.findValue("sslyze_version").asText())
                .outputFormat("JSON")
                .rawData(jsonRoot.toString())
                .build();
        baseMapper.insert(toolRawResultPO);
    }
}




