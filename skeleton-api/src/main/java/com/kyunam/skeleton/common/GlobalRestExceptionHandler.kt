package com.kyunam.skeleton.common

import com.kyunam.skeleton.common.exception.UnAuthorizationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler


@RestControllerAdvice(annotations = [RestController::class])
class GlobalRestExceptionHandler {
    @ExceptionHandler(value = [UnAuthorizationException::class])
    fun forbiddenExceptionHandler(exception: UnAuthorizationException): ResponseEntity<ApiErrorResponse> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiErrorResponse(
                status = HttpStatus.FORBIDDEN,
                errorMessage = exception.message ?: "권한이 없습니다.",
                debugMessage = exception.localizedMessage
        ))
    }
}
