package com.kyunam.skeleton.domain.account

import com.kyunam.skeleton.common.TestEntityCreateUtil
import com.kyunam.skeleton.common.exception.AccountValidationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Account 엔티티 테스트")
internal class AccountTest {
    @Test
    @DisplayName("Account 엔티티 생성 테스트")
    fun `Create Account test`() {
        val account: Account = TestEntityCreateUtil.getTestAccount()
        assertThat(account.email).isEqualTo(TestEntityCreateUtil.ACCOUNT_EMAIL)
        assertThat(account.password).isEqualTo(TestEntityCreateUtil.ACCOUNT_PASSWORD)
        assertThat(account.username).isEqualTo(TestEntityCreateUtil.ACCOUNT_USERNAME)
    }

    @Test
    @DisplayName("Account 엔티티 생성 Validation 테스트")
    fun `Create Fail Account test`() {
        val exception = assertThrows(AccountValidationException::class.java) { Account(email = "", password = TestEntityCreateUtil.ACCOUNT_PASSWORD, username = TestEntityCreateUtil.ACCOUNT_USERNAME) }
        assertThat(exception.message).isEqualTo("이메일은 필수 정보입니다.")
    }
}
