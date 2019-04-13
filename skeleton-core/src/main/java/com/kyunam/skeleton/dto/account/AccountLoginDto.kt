package com.kyunam.skeleton.dto.account

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class AccountLoginDto (
        @field:Email
        var email: String,
        @field:NotEmpty
        var password: String
)
