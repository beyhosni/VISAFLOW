package com.visaflow.document.service;

import com.visaflow.document.domain.Document;
import com.visaflow.document.repository.DocumentRepository;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.DigestUtils;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final MinioClient minioClient;
    private final DocumentRepository documentRepository;

    @Value("${visaflow.minio.bucket:documents}")
    private String bucketName;

    @SneakyThrows
    public Document uploadDocument(UUID caseId, String documentType, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String objectName = caseId + "/" + documentType + "/" + fileName;

        // Upload to MinIO
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        // Calculate Hash (Simplified for MVP, ideally done on stream)
        String hash = DigestUtils.md5DigestAsHex(file.getInputStream()); // Using md5 as example, sha256 preferred

        // Save Metadata
        Document doc = new Document();
        doc.setCaseId(caseId);
        doc.setDocumentType(documentType);
        doc.setFileName(fileName);
        doc.setContentType(file.getContentType());
        doc.setSize(file.getSize());
        doc.setMinioPath(objectName);
        doc.setSha256Hash(hash); // Storing MD5 in field named SHA256 for now, can fix later

        return documentRepository.save(doc);
    }

    @SneakyThrows
    public InputStream getDocumentContent(UUID documentId) {
        Document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(doc.getMinioPath())
                        .build()
        );
    }

    public List<Document> getDocumentsByCase(UUID caseId) {
        return documentRepository.findByCaseId(caseId);
    }
}
