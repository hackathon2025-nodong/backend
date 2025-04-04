package org.foreignworker.hackatone.global.error;

import lombok.Getter;

@Getter
public class ErrorMessage {
  private final String code;
  private final String message;
  
  public ErrorMessage() {
    this.code = null;
    this.message = null;
  }
  
  public ErrorMessage(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
  }
} 