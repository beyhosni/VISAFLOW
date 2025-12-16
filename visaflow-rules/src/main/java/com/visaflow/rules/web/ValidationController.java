package com.visaflow.rules.web;

import com.visaflow.rules.domain.ValidationResult;
import com.visaflow.rules.dto.CaseData;
import com.visaflow.rules.service.RuleEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@RequiredArgsConstructor
public class ValidationController {

    private final RuleEngine ruleEngine;

    @PostMapping("/validate")
    public ResponseEntity<List<ValidationResult>> validate(@RequestBody CaseData caseData) {
        return ResponseEntity.ok(ruleEngine.validate(caseData));
    }
}
