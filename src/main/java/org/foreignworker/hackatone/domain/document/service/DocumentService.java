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

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final S3Service s3Service;

    @Transactional
    public Document saveDocument(User user, DocumentType documentType, String fileUrl, Date issueDate, Date expiryDate) {
        Document document = new Document();
        document.setUser(user);
        document.setDocumentType(documentType);
        document.setOriginUrl(fileUrl);
        document.setIssueDate(issueDate);
        document.setExpiryDate(expiryDate);
        document.setExtractedData(""); // OCR 처리 결과를 저장할 필드

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