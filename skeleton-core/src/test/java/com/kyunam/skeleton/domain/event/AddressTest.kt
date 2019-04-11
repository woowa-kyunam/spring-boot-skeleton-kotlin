package com.kyunam.skeleton.domain.event

import com.kyunam.skeleton.common.exception.EventValidationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Attendance 테스트")
class AddressTest {
    @Test
    @DisplayName("Attendance 객체 생성 테스트")
    fun `create address test`() {
        val address = Address(
                localAddress = "지번 주소",
                roadAddress = "도로명 주소",
                postalCode = "12345"
        )
        assertThat(address.localAddress).isEqualTo("지번 주소")
        assertThat(address.roadAddress).isEqualTo("도로명 주소")
        assertThat(address.postalCode).isEqualTo("12345")
    }

    @Test
    @DisplayName("Attendance 객체 생성 Validation 테스트")
    fun `create fail address test`() {
        val exception = Assertions.assertThrows(EventValidationException::class.java) {Address(
                localAddress = "",
                roadAddress = "도로명 주소",
                postalCode = "12345"
        ) }
        assertThat(exception.message).isEqualTo("지번 주소는 필수 정보입니다.")
    }

    @Test
    @DisplayName("온라인 이벤트 주소 객체 생성 테스트")
    fun `create address online`() {
        val address = Address.ofOnlineEvent()
        assertThat(address.isOnline).isTrue()
    }

    @Test
    @DisplayName("주소 객체 empty 여부 테스트")
    fun `is empty address test`() {
        val onlineAddress = Address.ofOnlineEvent()
        assertThat(onlineAddress.isEmpty()).isFalse()
        val offlineAddress = Address(
                localAddress = "지번 주소",
                roadAddress = "도로명 주소",
                postalCode = "12345"
        )
        assertThat(offlineAddress.isEmpty()).isFalse()
    }
}
