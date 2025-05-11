package org.foreignworker.hackatone.domain.document.controller;

import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.document.Document;
import org.foreignworker.hackatone.domain.document.Document.DocumentType;
import org.foreignworker.hackatone.domain.document.service.DocumentService;
import org.foreignworker.hackatone.domain.user.security.CustomUserDetails;
import org.foreignworker.hackatone.global.service.S3Service;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final S3Service s3Service;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDocument(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentType") DocumentType documentType,
            @RequestParam(value = "issueDate", required = false) Date issueDate,
            @RequestParam(value = "expiryDate", required = false) Date expiryDate) throws IOException {

        String fileUrl = s3Service.uploadDocument(file, documentType);
        Document document = documentService.saveDocument(
                userDetails.getUser(),
                documentType,
                fileUrl,
                issueDate,
                expiryDate
        );

        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "문서가 성공적으로 업로드되었습니다.",
                "documentId", document.getDocumentId(),
                "fileUrl", document.getOriginUrl()
        ));
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<?> getDocument(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer documentId) {
        Document document = documentService.getDocument(documentId, userDetails.getUser());
        return ResponseEntity.ok(document);
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<?> deleteDocument(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer documentId) {
        documentService.deleteDocument(documentId, userDetails.getUser());
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "문서가 성공적으로 삭제되었습니다."
        ));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDocuments(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(documentService.getUserDocuments(userDetails.getUser()));
    }
} 