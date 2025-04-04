package org.foreignworker.hackatone.global.error.exception;

import org.foreignworker.hackatone.global.error.ErrorCode;

public class EntityNotFoundException extends ApiException {
  public EntityNotFoundException(String message) {
    super(message, ErrorCode.ENTITY_NOT_FOUND);
  }
} 