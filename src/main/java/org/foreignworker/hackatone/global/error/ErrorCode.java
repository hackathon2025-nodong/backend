package org.foreignworker.hackatone.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  // Common
  INVALID_INPUT_VALUE(400, "C001", "잘못된 입력값입니다"),
  ENTITY_NOT_FOUND(404, "C002", "리소스를 찾을 수 없습니다"),
  INTERNAL_SERVER_ERROR(500, "C003", "서버 내부 오류가 발생했습니다"),
  
  // User
  USER_NOT_FOUND(404, "U001", "사용자를 찾을 수 없습니다"),
  INVALID_CREDENTIAL(401, "U002", "로그인 정보가 올바르지 않습니다"),
  
  // Document
  DOCUMENT_NOT_FOUND(404, "D001", "문서를 찾을 수 없습니다"),
  DOCUMENT_UPLOAD_FAILED(500, "D002", "문서 업로드에 실패했습니다"),
  DOCUMENT_PROCESSING_FAILED(500, "D003", "문서 처리에 실패했습니다"),
  INVALID_DOCUMENT_TYPE(400, "D004", "지원되지 않는 문서 형식입니다"),
  
  // Analysis
  ANALYSIS_FAILED(500, "A001", "분석에 실패했습니다"),
  OCR_PROCESSING_FAILED(500, "A002", "OCR 처리에 실패했습니다"),

  // Case
  CASE_NOT_FOUND(404, "CS001", "케이스를 찾을 수 없습니다"),
  INVALID_CASE_STATUS(400, "CS002", "잘못된 케이스 상태입니다"),
  
  // Country
  COUNTRY_NOT_FOUND(404, "CT001", "국가 정보를 찾을 수 없습니다"),
  UNSUPPORTED_LANGUAGE(400, "CT002", "지원되지 않는 언어입니다"),
  
  // File
  FILE_NOT_FOUND(404, "F001", "파일을 찾을 수 없습니다"),
  FILE_SIZE_EXCEEDED(400, "F002", "파일 크기가 제한을 초과했습니다"),
  INVALID_FILE_FORMAT(400, "F003", "지원되지 않는 파일 형식입니다");
  
  private final int status;
  private final String code;
  private final String message;
} 