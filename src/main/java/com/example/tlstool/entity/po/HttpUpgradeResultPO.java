package com.example.tlstool.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value ="http_upgrade_result")
public class HttpUpgradeResultPO {
    /**
     * id
     */
    @TableId(value = "http_upgrade_result_id", type = IdType.AUTO)
    Long httpUpgradeResultId;

    /**
     * 目标id
     */
    @TableField(value = "target_id")
    Long targetId;

    /**
     * 目标
     */
    @TableField(value = "target")
    String target;

    /**
     * 是否重定向至https
     */
    @TableField(value = "status")
    Boolean status;

    /**
     * 最终url
     */
    @TableField(value = "location")
    String location;

    /**
     * 错误信息
     */
    @TableField(value = "error_message")
    String errorMessage;
}
