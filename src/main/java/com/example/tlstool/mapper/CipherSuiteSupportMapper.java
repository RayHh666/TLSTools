package com.example.tlstool.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.tlstool.entity.dto.CipherSuiteInfoDTO;
import com.example.tlstool.entity.dto.ProtocolInfoDTO;
import com.example.tlstool.entity.po.CipherSuiteSupportPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author admin
* @description 针对表【cipher_suite_support】的数据库操作Mapper
* @createDate 2025-08-03 18:37:45
* @Entity com.example.tlstool.entity/po.CipherSuiteSupport
*/
@Mapper
public interface CipherSuiteSupportMapper extends BaseMapper<CipherSuiteSupportPO> {
    List<CipherSuiteInfoDTO> getCipherSuiteInfoPageByTaskId(@Param("taskId") Long taskId, @Param("offset") int offset, @Param("size") int size);
}




