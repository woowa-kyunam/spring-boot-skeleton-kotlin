package com.kyunam.skeleton.dto.account

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kyunam.skeleton.domain.account.Account
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

class AccountDto {
    data class AccountLoginDto(
            @field:Email
            val email: String,
            @field:NotEmpty
            val password: String
    )

    data class AccountRequestDto(
            @field:Email
            val email: String,
            @field:Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})")
            val password: String,
            @field:NotEmpty
            val username: String
    )

    data class AccountResponseDto(
            @field:JsonIgnore
            val id: Long,
            val email: String,
            val username: String
    ) {
        companion object {
            fun toAccountResponseDto(account: Account?) = AccountResponseDto(
                    id = account!!.id,
                    email = account.email,
                    username = account.username
            )
        }
    }
}
