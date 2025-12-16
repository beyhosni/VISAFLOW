package com.visaflow.rules.dto;

import java.util.Map;
import java.util.List;

// Data required by the rule engine to validate a case
public record CaseData(
    Map<String, Object> answers,
    List<String> uploadedDocumentTypes
) {}
