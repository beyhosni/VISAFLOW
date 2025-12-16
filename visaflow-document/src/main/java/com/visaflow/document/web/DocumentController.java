package com.visaflow.document.web;

import com.visaflow.document.domain.Document;
import com.visaflow.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cases/{caseId}/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping
    public ResponseEntity<Document> uploadDocument(
            @PathVariable UUID caseId,
            @RequestParam("type") String type,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(documentService.uploadDocument(caseId, type, file));
    }

    @GetMapping
    public ResponseEntity<List<Document>> listDocuments(@PathVariable UUID caseId) {
        return ResponseEntity.ok(documentService.getDocumentsByCase(caseId));
    }

    @GetMapping("/{documentId}/download")
    public ResponseEntity<InputStreamResource> downloadDocument(@PathVariable UUID documentId) {
        // Need to fetch Document entity again inside service to get name/type, simplified here
        // Ideally Service returns a wrapper with metadata + stream
        
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(documentService.getDocumentContent(documentId)));
    }
}
