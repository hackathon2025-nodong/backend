package org.foreignworker.hackatone.domain.document.service;

import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.document.Document;
import org.foreignworker.hackatone.domain.document.Document.DocumentType;
import org.foreignworker.hackatone.domain.document.repository.DocumentRepository;
import org.foreignworker.hackatone.domain.user.entity.User;
import org.foreignworker.hackatone.global.error.ErrorCode;
import org.foreignworker.hackatone.global.error.exception.ApiException;
import org.foreignworker.hackatone.global.service.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final S3Service s3Service;
    private final GeminiService geminiService;

    @Transactional
    public Document saveDocument(MultipartFile file, String prompt, User user) throws IOException {
        // Upload to S3
        String s3Url = s3Service.uploadFile(file);

        // Analyze with Gemini
        String analysis = geminiService.analyzeDocument(file, prompt);

        // Create and save document
        Document document = Document.builder()
                .user(user)
                .documentType(DocumentType.IMAGE)
                .s3Url(s3Url)
                .analysis(analysis)
                .build();

        return documentRepository.save(document);
    }

    @Transactional(readOnly = true)
    public Document getDocument(Integer documentId, User user) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ApiException("문서를 찾을 수 없습니다.", ErrorCode.DOCUMENT_NOT_FOUND));

        if (!document.getUser().getUid().equals(user.getUid())) {
            throw new ApiException("해당 문서에 대한 접근 권한이 없습니다.", ErrorCode.INVALID_INPUT_VALUE);
        }

        return document;
    }

    @Transactional
    public void deleteDocument(Integer documentId, User user) {
        Document document = getDocument(documentId, user);
        s3Service.deleteFile(document.getOriginUrl());
        documentRepository.delete(document);
    }

    @Transactional(readOnly = true)
    public List<Document> getUserDocuments(User user) {
        return documentRepository.findByUser(user);
    }
} 