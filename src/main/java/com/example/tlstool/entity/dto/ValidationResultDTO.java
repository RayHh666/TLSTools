package com.example.tlstool.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResultDTO {
    private String trustStoreName;
    private String trustStoreVersion;
    private String validationError;
    private String wasValidationSuccessful;
}
