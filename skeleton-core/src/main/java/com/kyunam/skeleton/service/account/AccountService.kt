package com.kyunam.skeleton.service.account

import com.kyunam.skeleton.common.CustomMessageUtil
import com.kyunam.skeleton.common.exception.AccountValidationException
import com.kyunam.skeleton.domain.account.Account
import com.kyunam.skeleton.dto.account.AccountDto
import com.kyunam.skeleton.repository.account.AccountRepository
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AccountService (
        private val accountRepository: AccountRepository,
        private val delegatingPasswordEncoder: PasswordEncoder,
        private val messageSourceAccessor: MessageSourceAccessor
) {
    @Transactional
    fun createAccount(accountRequestDto: AccountDto.AccountRequestDto): AccountDto.AccountResponseDto {
        var account = Account(
                email = accountRequestDto.email,
                username = accountRequestDto.username,
                password = delegatingPasswordEncoder.encode(accountRequestDto.password)
        )
        val savedAccount = accountRepository.save(account)
        return AccountDto.AccountResponseDto(
                id = savedAccount.id,
                email = savedAccount.email,
                username = savedAccount.username
        )
    }

    fun getAccount(id: Long): Account {
        return accountRepository.findById(id).orElseThrow{AccountValidationException(messageSourceAccessor.getMessage(CustomMessageUtil.ACCOUNT_NOTFOUND))}
    }

    fun login(accountLoginDto: AccountDto.AccountLoginDto): Account  {
        return accountRepository.findByEmail(accountLoginDto.email)
                .filter{a -> a.isMatchPassword(accountLoginDto.password, delegatingPasswordEncoder)}
                .orElseThrow{AccountValidationException(messageSourceAccessor.getMessage(CustomMessageUtil.INVALID_ACCOUNT))}
    }
}
