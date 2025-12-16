package com.visaflow.questionnaire.domain;

import java.util.List;

public record QuestionDefinition(
    String id,
    String label,
    QuestionType type,
    boolean required,
    List<String> options, // For dropdowns/radios
    String validationRegex
) {}
