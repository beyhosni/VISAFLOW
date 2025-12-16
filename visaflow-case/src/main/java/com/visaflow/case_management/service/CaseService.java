package com.visaflow.case_management.service;

import com.visaflow.case_management.domain.Case;
import com.visaflow.case_management.dto.CaseDTO;
import com.visaflow.case_management.dto.CreateCaseRequest;
import com.visaflow.case_management.mapper.CaseMapper;
import com.visaflow.case_management.repository.CaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaseService {

    private final CaseRepository caseRepository;
    private final CaseMapper caseMapper;

    @Transactional
    public CaseDTO createCase(UUID userId, CreateCaseRequest request) {
        Case newCase = Case.create(userId, request.petitionerName(), request.beneficiaryName());
        Case savedCase = caseRepository.save(newCase);
        return caseMapper.toDTO(savedCase);
    }

    @Transactional(readOnly = true)
    public List<CaseDTO> getMyCases(UUID userId) {
        return caseRepository.findByUserId(userId).stream()
                .map(caseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CaseDTO getCase(UUID caseId) {
        // In a real app, verify ownership here
        return caseRepository.findById(caseId)
                .map(caseMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Case not found"));
    }
}
