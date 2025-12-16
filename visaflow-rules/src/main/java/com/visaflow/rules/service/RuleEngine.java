package com.visaflow.rules.service;

import com.visaflow.rules.domain.ValidationResult;
import com.visaflow.rules.domain.ValidationSeverity;
import com.visaflow.rules.dto.CaseData;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RuleEngine {

    public List<ValidationResult> validate(CaseData data) {
        List<ValidationResult> results = new ArrayList<>();
        Map<String, Object> answers = data.answers();
        List<String> documents = data.uploadedDocumentTypes();

        // --- 1. Basic Answer Validation (Example: DOB) ---
        if (answers.containsKey("p_dob")) {
            String dob = (String) answers.get("p_dob");
            try {
                LocalDate date = LocalDate.parse(dob);
                if (date.isAfter(LocalDate.now())) {
                    results.add(new ValidationResult("RULE_001", "Date of birth cannot be in the future", ValidationSeverity.ERROR, "p_dob"));
                }
            } catch (DateTimeParseException e) {
                results.add(new ValidationResult("RULE_002", "Invalid date format", ValidationSeverity.ERROR, "p_dob"));
            }
        } else {
             results.add(new ValidationResult("RULE_003", "Date of birth is missing", ValidationSeverity.ERROR, "p_dob"));
        }

        // --- 2. Document Consistency ---
        // If citizenship is NATURALIZED, require Naturalization Certificate
        String citizenship = (String) answers.get("p_citizen_status");
        if ("NATURALIZED".equals(citizenship)) {
            if (!documents.contains("NATURALIZATION_CERTIFICATE")) {
                results.add(new ValidationResult("RULE_DOC_001", "Naturalization Certificate is required for Naturalized Citizens", ValidationSeverity.ERROR, "NATURALIZATION_CERTIFICATE"));
            }
        }

        return results;
    }
}
