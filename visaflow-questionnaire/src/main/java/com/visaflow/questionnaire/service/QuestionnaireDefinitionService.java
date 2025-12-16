package com.visaflow.questionnaire.service;

import com.visaflow.questionnaire.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionnaireDefinitionService {

    // Hardcoded for MVP, ideally loaded from DB or external config
    public List<QuestionDefinition> getQuestions(String type) {
        if ("I-130-PETITIONER".equals(type)) {
            return List.of(
                new QuestionDefinition("p_first_name", "First Name", QuestionType.TEXT, true, null, null),
                new QuestionDefinition("p_last_name", "Last Name", QuestionType.TEXT, true, null, null),
                new QuestionDefinition("p_dob", "Date of Birth", QuestionType.DATE, true, null, null),
                new QuestionDefinition("p_citizen_status", "Citizenship Status", QuestionType.SELECT, true, List.of("US_BORN", "NATURALIZED"), null)
            );
        }
        return List.of();
    }
}
