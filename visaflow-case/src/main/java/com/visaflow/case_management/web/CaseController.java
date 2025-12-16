package com.visaflow.case_management.web;

import com.visaflow.case_management.dto.CaseDTO;
import com.visaflow.case_management.dto.CreateCaseRequest;
import com.visaflow.case_management.service.CaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cases")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @PostMapping
    public ResponseEntity<CaseDTO> createCase(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid CreateCaseRequest request) {
        UUID userId = UUID.fromString(jwt.getSubject());
        CaseDTO createdCase = caseService.createCase(userId, request);
        return ResponseEntity.ok(createdCase);
    }

    @GetMapping
    public ResponseEntity<List<CaseDTO>> getMyCases(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(caseService.getMyCases(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CaseDTO> getCase(@PathVariable UUID id) {
         return ResponseEntity.ok(caseService.getCase(id));
    }
}
