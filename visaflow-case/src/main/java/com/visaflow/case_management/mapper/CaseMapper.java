package com.visaflow.case_management.mapper;

import com.visaflow.case_management.domain.Case;
import com.visaflow.case_management.dto.CaseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CaseMapper {
    CaseDTO toDTO(Case caseEntity);
}
