package com.kyunam.skeleton.domain.event

import com.kyunam.skeleton.common.TestObjectCreateUtil
import com.kyunam.skeleton.common.exception.EventValidationException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Event 도메인 테스트")
class EventTest {
    @Test
    @DisplayName("Event 생성 테스트")
    fun `create event test`() {
        val event = TestObjectCreateUtil.getTestEvent()
        assertThat(event.name).isEqualTo(TestObjectCreateUtil.EVENT_NAME)
    }

    @Test
    @DisplayName("Event 객체 생성 Validation 테스트")
    fun `create fail event test`() {
        val exception = org.junit.jupiter.api.Assertions.assertThrows(EventValidationException::class.java) {
            Event(
                    name = "",
                    contents = TestObjectCreateUtil.EVENT_CONTENTS,
                    address = TestObjectCreateUtil.getTestAddress(),
                    price = TestObjectCreateUtil.EVENT_PRICE,
                    availableParticipant = TestObjectCreateUtil.EVENT_AVAILABLE_PARTICIPANT,
                    beginEnrollmentDateTime = TestObjectCreateUtil.EVENT_BEGIN_ENROLLMENT_DATETIME,
                    endEnrollmentDateTime = TestObjectCreateUtil.EVENT_BEGIN_ENROLLMENT_DATETIME.plusDays(1),
                    beginEventDateTime = TestObjectCreateUtil.EVENT_BEGIN_EVENT_DATETIME,
                    endEventDateTime = TestObjectCreateUtil.EVENT_BEGIN_EVENT_DATETIME.plusHours(8)
            )
        }
        Assertions.assertThat(exception.message).isEqualTo("이벤트 이름은 필수 정보입니다.")
    }
}
