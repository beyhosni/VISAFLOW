package com.visaflow.questionnaire.dto;

import java.util.Map;

public record SubmitAnswersRequest(
    Map<String, Object> answers
) {}
