package com.example.tlstool.entity.dto;

import com.example.tlstool.entity.po.CertificateDetailPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateChainDTO {
    private List<CertificateDetailPO> certificateDetailPOList;
    private List<ValidationResultDTO> validationResultDTOList;
}
