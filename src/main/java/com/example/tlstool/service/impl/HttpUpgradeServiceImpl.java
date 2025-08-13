package com.example.tlstool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tlstool.entity.po.HttpUpgradeResultPO;
import com.example.tlstool.entity.po.ScanTargetPO;
import com.example.tlstool.mapper.HttpUpgradeMapper;
import com.example.tlstool.mapper.ScanTargetMapper;
import com.example.tlstool.service.HttpUpgradeService;
import com.example.tlstool.service.ScanTargetService;
import org.springframework.stereotype.Service;

@Service
public class HttpUpgradeServiceImpl extends ServiceImpl<HttpUpgradeMapper, HttpUpgradeResultPO>
        implements HttpUpgradeService {
}
