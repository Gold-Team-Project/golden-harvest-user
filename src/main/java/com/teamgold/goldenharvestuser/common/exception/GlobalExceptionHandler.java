package com.teamgold.goldenharvestuser.common.exception;

import com.teamgold.goldenharvestuser.common.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.teamgold.goldenharvestuser.common.exception.ErrorCode.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("BusinessException: {}: {}", errorCode.getCode(), errorCode.getMessage(), e);
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ApiResponse.fail(errorCode, errorCode.getMessage()));
    }

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<?>> handleConstraintViolation(
		ConstraintViolationException e
	) {
		return ResponseEntity.badRequest()
			.body(ApiResponse.fail(INVALID_REQUEST, e.getMessage()));
	}
}
