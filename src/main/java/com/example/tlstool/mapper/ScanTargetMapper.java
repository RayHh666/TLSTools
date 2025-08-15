package com.example.tlstool.mapper;

import com.example.tlstool.entity.dto.ProtocolInfoDTO;
import com.example.tlstool.entity.po.ScanTargetPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author admin
* @description 针对表【scan_target】的数据库操作Mapper
* @createDate 2025-08-03 18:37:45
* @Entity com.example.tlstool.entity/po.ScanTarget
*/
@Mapper
public interface ScanTargetMapper extends BaseMapper<ScanTargetPO> {
    List<ProtocolInfoDTO> getProtocolInfoPageByTaskId(@Param("taskId") Long taskId, @Param("offset") int offset, @Param("size") int size);

    Integer getProtocolInfoTotalByTaskId(@Param("taskId") Long taskId);
}




