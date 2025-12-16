package com.visaflow.case_management.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCaseRequest(
    @NotBlank String petitionerName,
    @NotBlank String beneficiaryName
) {}
