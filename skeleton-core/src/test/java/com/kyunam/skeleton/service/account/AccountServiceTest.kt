package com.kyunam.skeleton.service.account

import com.kyunam.skeleton.common.CustomMessageUtil
import com.kyunam.skeleton.common.TestObjectCreateUtil
import com.kyunam.skeleton.common.exception.AccountValidationException
import com.kyunam.skeleton.common.exception.EventValidationException
import com.kyunam.skeleton.domain.account.Account
import com.kyunam.skeleton.dto.account.AccountRequestDto
import com.kyunam.skeleton.dto.account.AccountResponseDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.transaction.annotation.Transactional


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DisplayName("계정 서비스 테스트")
@Transactional
internal class AccountServiceTest {
    @Autowired
    private lateinit var accountService: AccountService
    @Autowired
    private lateinit var messageSourceAccessor: MessageSourceAccessor

    @Test
    fun `Create account and getAccount test`() {
        var accountRequestDto: AccountRequestDto = TestObjectCreateUtil.getTestAccountRequestDto()
        var accountResponseDto: AccountResponseDto = accountService.createAccount(accountRequestDto)
        var savedAccount: Account = accountService.getAccount(accountResponseDto.id)
        assertThat(savedAccount.createDate).isNotNull()
        assertThat(savedAccount.lastModifiedDate).isNotNull()
        assertThat(accountResponseDto.email).isEqualTo(savedAccount.email)
    }

    @Test
    fun `Get account failure test`() {
        val exception = Assertions.assertThrows(AccountValidationException::class.java) {
            accountService.getAccount(0L)
        }
        assertThat(exception.message).isEqualTo(messageSourceAccessor.getMessage(CustomMessageUtil.ACCOUNT_NOTFOUND))
    }
}
