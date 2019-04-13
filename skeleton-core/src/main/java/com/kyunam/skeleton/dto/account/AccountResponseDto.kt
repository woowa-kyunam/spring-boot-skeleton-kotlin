package com.kyunam.skeleton.dto.account

import com.fasterxml.jackson.annotation.JsonIgnore

data class AccountResponseDto (
        @field:JsonIgnore
        val id: Long,
        val email: String,
        val username: String
)
