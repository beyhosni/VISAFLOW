package com.visaflow.ai.service;

import org.springframework.stereotype.Service;

@Service
public class AiOrchestrator {

    /**
     * Stub for AI Extraction.
     * In the future, this would call OpenAI/Gemini APIs to extract data from documents.
     * Constraints: No decision making, only extraction and normalization.
     */
    public String extractText(String documentContent) {
        // Mock implementation
        return "Extracted Text from: " + documentContent.substring(0, Math.min(documentContent.length(), 20)) + "...";
    }

    /**
     * Stub for Coherence Check.
     * Returns a list of potential inconsistencies (non-blocking warnings).
     */
    public String checkCoherence(String dataA, String dataB) {
         return "No inconsistencies found (AI Mock)";
    }
}
