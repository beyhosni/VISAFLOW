package com.visaflow.questionnaire.domain;

import com.visaflow.core.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "questionnaire_submissions")
@Getter
@Setter
public class QuestionnaireSubmission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID caseId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "answers", columnDefinition = "jsonb")
    private Map<String, Object> answers; // Key: QuestionID, Value: Answer

    private String questionnaireType; // e.g., "I-130-PETITIONER"

}
