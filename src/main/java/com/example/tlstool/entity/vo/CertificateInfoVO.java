package com.example.tlstool.entity.vo;

import com.example.tlstool.entity.dto.CertificateInfoDTO;
import lombok.Data;

import java.util.List;

@Data
public class CertificateInfoVO {
    private Integer total;

    private List<CertificateInfoDTO> certificateInfoDTOList;
}
