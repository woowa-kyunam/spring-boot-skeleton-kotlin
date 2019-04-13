package com.kyunam.skeleton.dto.account

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

data class AccountRequestDto (
        @field:Email
        val email: String,
        @field:Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
        val password: String,
        @field:NotEmpty
        val username: String
)
