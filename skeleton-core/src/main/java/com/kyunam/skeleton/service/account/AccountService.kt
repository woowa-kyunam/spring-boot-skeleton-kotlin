package com.kyunam.skeleton.service.account

import com.kyunam.skeleton.common.CustomMessageUtil
import com.kyunam.skeleton.common.exception.AccountValidationException
import com.kyunam.skeleton.domain.account.Account
import com.kyunam.skeleton.dto.account.AccountRequestDto
import com.kyunam.skeleton.dto.account.AccountResponseDto
import com.kyunam.skeleton.repository.account.AccountRepository
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AccountService (
        private val accountRepository: AccountRepository,
        private val passwordEncoder: PasswordEncoder,
        private val messageSourceAccessor: MessageSourceAccessor
) {
    @Transactional
    fun createAccount(accountRequestDto: AccountRequestDto): AccountResponseDto {
        var account: Account = Account(
                email = accountRequestDto.email,
                username = accountRequestDto.username,
                password = passwordEncoder.encode(accountRequestDto.password)
        )
        val savedAccount: Account = accountRepository.save(account)
        return AccountResponseDto(
                id = savedAccount.id,
                email = savedAccount.email,
                username = savedAccount.username
        )
    }

    fun getAccount(id: Long): Account {
        return accountRepository.findById(id).orElseThrow{AccountValidationException(messageSourceAccessor.getMessage(CustomMessageUtil.ACCOUNT_NOTFOUND))}
    }
}
