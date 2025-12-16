package com.visaflow.case_management.domain;

import com.visaflow.core.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "cases")
@Getter
@Setter
public class Case extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId; // Linked to Keycloak Subject ID

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseStatus status;

    private String petitionerName;
    private String beneficiaryName;
    
    // Future reference to workflow ID
    private String workflowId;

    public static Case create(UUID userId, String petitionerName, String beneficiaryName) {
        Case newCase = new Case();
        newCase.setUserId(userId);
        newCase.setPetitionerName(petitionerName);
        newCase.setBeneficiaryName(beneficiaryName);
        newCase.setStatus(CaseStatus.DRAFT);
        return newCase;
    }
}
