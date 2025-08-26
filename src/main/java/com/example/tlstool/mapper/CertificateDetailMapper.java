package com.example.tlstool.mapper;

import com.example.tlstool.entity.dto.CertificateInfoDTO;
import com.example.tlstool.entity.po.CertificateDetailPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author admin
* @description 针对表【certificate_detail】的数据库操作Mapper
* @createDate 2025-08-06 17:08:57
* @Entity com.example.tlstool.entity.po.CertificateDetail
*/
@Mapper
public interface CertificateDetailMapper extends BaseMapper<CertificateDetailPO> {
    List<CertificateInfoDTO> getCertificateInfoPageByTaskId(@Param("taskId") Long taskId, @Param("count") Integer count, @Param("offset") int offset, @Param("size") int size);

    Integer getCertificateInfoTotalByTaskId(@Param("taskId") Long taskId, @Param("count") Integer count);

    List<CertificateDetailPO> getCertificateChainDetail(@Param("certificateDeploymentId") Long certificateDeploymentId);
}




