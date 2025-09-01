package com.example.tlstool.entity.vo;

import com.example.tlstool.entity.dto.HttpUpgradeResultDTO;
import com.example.tlstool.entity.po.HttpUpgradeResultPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpUpgradeResultVO {

    private List<HttpUpgradeResultDTO> httpUpgradeResultDTOList;

    private Integer total;
}
