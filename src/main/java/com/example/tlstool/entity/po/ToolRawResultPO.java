package com.example.tlstool.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName tool_raw_result
 */
@TableName(value ="tool_raw_result")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToolRawResultPO {
    /**
     * 结果id
     */
    @TableId(value = "result_id", type = IdType.AUTO)
    private Long resultId;

    /**
     * 任务id
     */
    @TableField(value = "target_id")
    private Long targetId;

    /**
     * 工具名称
     */
    @TableField(value = "tool_name")
    private String toolName;

    /**
     * 工具版本
     */
    @TableField(value = "tool_version")
    private String toolVersion;

    /**
     * 'JSON','XML','TEXT'，默认JSON
     */
    @TableField(value = "output_format")
    private String outputFormat;

    /**
     * 原始数据
     */
    @TableField(value = "raw_data")
    private String rawData;

    /**
     * 是否已解析
     */
    @TableField(value = "parsed")
    private Integer parsed;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ToolRawResultPO other = (ToolRawResultPO) that;
        return (this.getResultId() == null ? other.getResultId() == null : this.getResultId().equals(other.getResultId()))
            && (this.getTargetId() == null ? other.getTargetId() == null : this.getTargetId().equals(other.getTargetId()))
            && (this.getToolName() == null ? other.getToolName() == null : this.getToolName().equals(other.getToolName()))
            && (this.getToolVersion() == null ? other.getToolVersion() == null : this.getToolVersion().equals(other.getToolVersion()))
            && (this.getOutputFormat() == null ? other.getOutputFormat() == null : this.getOutputFormat().equals(other.getOutputFormat()))
            && (this.getRawData() == null ? other.getRawData() == null : this.getRawData().equals(other.getRawData()))
            && (this.getParsed() == null ? other.getParsed() == null : this.getParsed().equals(other.getParsed()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getResultId() == null) ? 0 : getResultId().hashCode());
        result = prime * result + ((getTargetId() == null) ? 0 : getTargetId().hashCode());
        result = prime * result + ((getToolName() == null) ? 0 : getToolName().hashCode());
        result = prime * result + ((getToolVersion() == null) ? 0 : getToolVersion().hashCode());
        result = prime * result + ((getOutputFormat() == null) ? 0 : getOutputFormat().hashCode());
        result = prime * result + ((getRawData() == null) ? 0 : getRawData().hashCode());
        result = prime * result + ((getParsed() == null) ? 0 : getParsed().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", resultId=").append(resultId);
        sb.append(", taskId=").append(targetId);
        sb.append(", toolName=").append(toolName);
        sb.append(", toolVersion=").append(toolVersion);
        sb.append(", outputFormat=").append(outputFormat);
        sb.append(", rawData=").append(rawData);
        sb.append(", parsed=").append(parsed);
        sb.append("]");
        return sb.toString();
    }
}