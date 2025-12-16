package com.visaflow.questionnaire.web;

import com.visaflow.questionnaire.domain.QuestionDefinition;
import com.visaflow.questionnaire.dto.SubmitAnswersRequest;
import com.visaflow.questionnaire.service.QuestionnaireDefinitionService;
import com.visaflow.questionnaire.service.QuestionnaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/cases/{caseId}/questionnaire")
@RequiredArgsConstructor
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;
    private final QuestionnaireDefinitionService definitionService;

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionDefinition>> getQuestions(@RequestParam(defaultValue = "I-130-PETITIONER") String type) {
        return ResponseEntity.ok(definitionService.getQuestions(type));
    }

    @PostMapping("/answers")
    public ResponseEntity<Map<String, Object>> submitAnswers(@PathVariable UUID caseId, @RequestBody SubmitAnswersRequest request) {
        var submission = questionnaireService.saveAnswers(caseId, request.answers());
        return ResponseEntity.ok(submission.getAnswers());
    }

    @GetMapping("/answers")
    public ResponseEntity<Map<String, Object>> getAnswers(@PathVariable UUID caseId) {
        return ResponseEntity.ok(questionnaireService.getAnswers(caseId));
    }
}
