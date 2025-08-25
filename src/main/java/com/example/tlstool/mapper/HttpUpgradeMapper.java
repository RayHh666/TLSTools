package com.example.tlstool.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tlstool.entity.dto.VulnerabilityDetectionResultDTO;
import com.example.tlstool.entity.po.HttpUpgradeResultPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HttpUpgradeMapper extends BaseMapper<HttpUpgradeResultPO> {

    List<HttpUpgradeResultPO> getHttpRedirectionResultList(@Param("taskId") Long taskId,
                                                 @Param("count") Integer count,
                                                 @Param("offset") int offset,
                                                 @Param("size") int size);


    Integer getHttpRedirectionResultTotal(@Param("taskId") Long taskId, @Param("count") Integer count);
}
