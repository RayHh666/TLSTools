package com.example.tlstool.entity.vo;

import com.example.tlstool.entity.dto.CipherSuiteInfoDTO;
import lombok.Data;

import java.util.List;

@Data
public class CipherSuiteInfoVO {

    private Integer total;

    private List<CipherSuiteInfoDTO> cipherSuiteInfoDTOList;
}
