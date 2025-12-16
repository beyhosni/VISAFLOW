package com.visaflow.case_management.dto;

import com.visaflow.case_management.domain.CaseStatus;
import java.util.UUID;

public record CaseDTO(
    UUID id,
    CaseStatus status,
    String petitionerName,
    String beneficiaryName
) {}
