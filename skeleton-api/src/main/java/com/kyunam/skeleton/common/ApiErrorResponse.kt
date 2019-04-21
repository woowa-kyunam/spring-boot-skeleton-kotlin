package com.kyunam.skeleton.common

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ApiErrorResponse(
        var status: HttpStatus?,
        @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        var timestamp: LocalDateTime = LocalDateTime.now(),
        var errorMessage: String?,
        var debugMessage: String?
)
