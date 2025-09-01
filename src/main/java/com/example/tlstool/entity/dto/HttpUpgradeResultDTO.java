package com.example.tlstool.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
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
     * 目标
     */
    String target;

    /**
     * 是否重定向至https
     */
    Boolean status;

    /**
     * 最终url
     */
    String location;

    /**
     * 错误信息
     */
    String errorMessage;
}
