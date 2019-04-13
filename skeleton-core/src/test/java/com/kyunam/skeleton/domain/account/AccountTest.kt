package com.kyunam.skeleton.domain.account

import com.kyunam.skeleton.common.TestObjectCreateUtil
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
        val account: Account = TestObjectCreateUtil.getTestAccount()
        assertThat(account.email).isEqualTo(TestObjectCreateUtil.ACCOUNT_EMAIL)
        assertThat(account.password).isEqualTo(TestObjectCreateUtil.ACCOUNT_PASSWORD)
        assertThat(account.username).isEqualTo(TestObjectCreateUtil.ACCOUNT_USERNAME)
    }

    @Test
    @DisplayName("Account 엔티티 생성 Validation 테스트")
    fun `Create Fail Account test`() {
        val exception = assertThrows(AccountValidationException::class.java) { Account(email = "", password = TestObjectCreateUtil.ACCOUNT_PASSWORD, username = TestObjectCreateUtil.ACCOUNT_USERNAME) }
        assertThat(exception.message).isEqualTo("이메일은 필수 정보입니다.")
    }
}
