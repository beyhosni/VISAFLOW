package com.visaflow.rules.domain;

public record ValidationResult(
    String ruleId,
    String message,
    ValidationSeverity severity,
    String fieldOrDocumentId // The source of the error
) {}
