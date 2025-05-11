package org.foreignworker.hackatone.global.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.foreignworker.hackatone.domain.document.Document.DocumentType;
import org.foreignworker.hackatone.global.error.ErrorCode;
import org.foreignworker.hackatone.global.error.exception.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
            "image/jpeg", "image/jpg", "image/png", "image/pdf"
    );

    private static final List<String> DOC_TYPES = Arrays.asList(
      "급여명세서","근로계약서"
    );
    //서류 업로드시 앞에 급여명세서/ 이런 카테고리 형태로 이미지에 붙이기

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 파일 유효성 검사
    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ApiException("파일이 비어있습니다.", ErrorCode.INVALID_INPUT_VALUE);
        }
        if (!ALLOWED_IMAGE_TYPES.contains(file.getContentType())) {
            throw new ApiException("지원하지 않는 파일 형식입니다.", ErrorCode.INVALID_FILE_FORMAT);
        }
        if (file.getSize() > 10 * 1024 * 1024) { // 10MB
            throw new ApiException("파일 크기가 제한을 초과했습니다.", ErrorCode.FILE_SIZE_EXCEEDED);
        }
    }

    // 프로필 이미지 업로드
    public String uploadFile(MultipartFile file) throws IOException {
        validateFile(file);
        return uploadToS3(file, "profile-images");
    }

    // 문서 업로드
    public String uploadDocument(MultipartFile file, DocumentType documentType) throws IOException {
        validateFile(file);
        String directory = getDocumentDirectory(documentType);
        return uploadToS3(file, directory);
    }

    // 문서 타입별 디렉토리 결정
    private String getDocumentDirectory(DocumentType documentType) {
        return switch (documentType) {
            case Residence -> "documents/residence";
            case PayStub -> "documents/paystub";
            case EmployContract -> "documents/contract";
        };
    }

    // S3 업로드 공통 로직
    private String uploadToS3(MultipartFile file, String directory) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String s3Key = directory + "/" + fileName;

        amazonS3Client.putObject(new PutObjectRequest(
                bucket,
                s3Key,
                file.getInputStream(),
                metadata
        ));

        return amazonS3Client.getUrl(bucket, s3Key).toString();
    }

    public void deleteFile(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            String directory = fileUrl.substring(fileUrl.indexOf(bucket) + bucket.length() + 1, fileUrl.lastIndexOf("/"));
            amazonS3Client.deleteObject(bucket, directory + "/" + fileName);
        } catch (Exception e) {
            throw new ApiException("파일 삭제 중 오류가 발생했습니다: " + e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
