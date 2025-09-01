package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.dto.HttpUpgradeResultDTO;
import com.example.tlstool.entity.dto.VulnerabilityDetectionResultDTO;
import com.example.tlstool.entity.po.HttpUpgradeResultPO;
import com.example.tlstool.entity.po.ScanTargetPO;
import com.example.tlstool.entity.vo.HttpUpgradeResultVO;
import com.example.tlstool.entity.vo.VulnerabilityDetectionResultVO;
import com.example.tlstool.mapper.HttpUpgradeMapper;
import com.example.tlstool.mapper.ScanTargetMapper;
import com.example.tlstool.service.HttpUpgradeService;
import com.example.tlstool.service.ScanTargetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HttpUpgradeServiceImpl extends ServiceImpl<HttpUpgradeMapper, HttpUpgradeResultPO>
        implements HttpUpgradeService {

    @Override
    public HttpUpgradeResultVO getHttpRedirectionResult(Long taskId, Integer count, int page, int size) {

        HttpUpgradeResultVO httpUpgradeResultVO = new HttpUpgradeResultVO();
        int offset = (page - 1) * size;
        List<HttpUpgradeResultDTO> httpUpgradeResultDTOList = baseMapper.getHttpRedirectionResultList(taskId, count, offset, size);
        Integer total = baseMapper.getHttpRedirectionResultTotal(taskId, count);
        httpUpgradeResultVO.setHttpUpgradeResultDTOList(httpUpgradeResultDTOList);
        httpUpgradeResultVO.setTotal(total);
        return httpUpgradeResultVO;
    }
}
