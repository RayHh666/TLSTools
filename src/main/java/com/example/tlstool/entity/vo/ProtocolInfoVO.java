package com.example.tlstool.entity.vo;

import com.example.tlstool.entity.dto.ProtocolInfoDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProtocolInfoVO {

    private Integer total;

    private List<ProtocolInfoDTO> protocolInfoDTOList;
}
