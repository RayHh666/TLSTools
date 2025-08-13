package com.example.tlstool.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpUpgradeResultDTO {
    /**
     * 是否重定向至https
     */
    Boolean status;

    /**
     * 最终url
     */
    String location;
}
