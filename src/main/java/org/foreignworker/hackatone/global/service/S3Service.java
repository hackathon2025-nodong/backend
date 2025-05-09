package org.foreignworker.hackatone.global.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

//    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
//            "image/jpeg", "image/jpg", "image/png", "image/gif"
//    );

    private static final List<String> DOC_TYPES = Arrays.asList(
      "급여명세서","근로계약서"
    );
    //서류 업로드시 앞에 급여명세서/ 이런 카테고리 형태로 이미지에 붙이기

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    //파일 검증?

    public String uploadFile(MultipartFile file) throws IOException {
        // 파일 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // 파일명 중복 방지를 위한 UUID 생성
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // S3에 파일 업로드
        amazonS3Client.putObject(new PutObjectRequest(
                bucket,
                "profile-images/" + fileName,
                file.getInputStream(),
                metadata
        ));

        // 업로드된 파일의 URL 반환
        return amazonS3Client.getUrl(bucket, "profile-images/" + fileName).toString();
    }

    public void deleteFile(String fileUrl) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            amazonS3Client.deleteObject(bucket, "profile-images/" + fileName);
        } catch (Exception e) {
            throw new RuntimeException("파일 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
