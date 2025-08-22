package com.example.tlstool.entity.dto;

import com.example.tlstool.entity.ro.TlsCreateTaskRO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TlsCreateTaskDTO extends TlsCreateTaskRO {
    private Long TaskId;
}
