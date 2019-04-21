package com.kyunam.skeleton.common

import com.kyunam.skeleton.domain.account.Account
import com.kyunam.skeleton.domain.event.Address
import com.kyunam.skeleton.domain.event.Event
import com.kyunam.skeleton.dto.account.AccountDto
import com.kyunam.skeleton.dto.event.EventDto
import java.time.LocalDateTime


class TestObjectCreateUtil {
    companion object {
        const val ACCOUNT_EMAIL = "tramyu@naver.com"
        const val ACCOUNT_PASSWORD = "12345678#123"
        private const val ACCOUNT_USERNAME = "규남"
        private const val EVENT_NAME = "공부할게 많은 이벤트"
        private const val EVENT_CONTENTS = "Golang, kotlin, typescript, k8s ..."
        private const val EVENT_PRICE = 100000
        private const val EVENT_AVAILABLE_PARTICIPANT = 20
        private val EVENT_BEGIN_ENROLLMENT_DATETIME: LocalDateTime = LocalDateTime.of(2100, 4, 15, 3, 1, 1).plusMinutes(1)
        private val EVENT_BEGIN_EVENT_DATETIME: LocalDateTime = EVENT_BEGIN_ENROLLMENT_DATETIME.plusMonths(1)
        fun getTestAccount(): Account {
            return Account(
                    email = ACCOUNT_EMAIL,
                    password = ACCOUNT_PASSWORD,
                    username = ACCOUNT_USERNAME
            )
        }

        private fun getTestAddress(): Address {
            return Address(
                    localAddress = "지번 주소",
                    roadAddress = "도로명 주소",
                    postalCode = "12345"
            )
        }

        fun getTestEvent(): Event {
            return Event(
                    name = EVENT_NAME,
                    contents = EVENT_CONTENTS,
                    address = getTestAddress(),
                    price = EVENT_PRICE,
                    register = getTestAccount(),
                    availableParticipant = EVENT_AVAILABLE_PARTICIPANT,
                    beginEnrollmentDateTime = EVENT_BEGIN_ENROLLMENT_DATETIME,
                    endEnrollmentDateTime = EVENT_BEGIN_ENROLLMENT_DATETIME.plusDays(1),
                    beginEventDateTime = EVENT_BEGIN_EVENT_DATETIME,
                    endEventDateTime = EVENT_BEGIN_EVENT_DATETIME.plusHours(8)
            )
        }

        fun getTestAccountRequestDto(): AccountDto.AccountRequestDto {
            return AccountDto.AccountRequestDto(
                    email = ACCOUNT_EMAIL,
                    password = ACCOUNT_PASSWORD,
                    username = ACCOUNT_USERNAME
            )
        }

        fun getTestAccountLoginDto(): AccountDto.AccountLoginDto {
            return AccountDto.AccountLoginDto(
                    email = ACCOUNT_EMAIL,
                    password = ACCOUNT_PASSWORD
            )
        }

        fun getTestEventRequestDto(): EventDto.EventRequestDto {
            return EventDto.EventRequestDto(
                    name = EVENT_NAME,
                    contents = EVENT_CONTENTS,
                    address = getTestAddress(),
                    price = EVENT_PRICE,
                    availableParticipant = EVENT_AVAILABLE_PARTICIPANT,
                    beginEnrollmentDateTime = EVENT_BEGIN_ENROLLMENT_DATETIME,
                    endEnrollmentDateTime = EVENT_BEGIN_ENROLLMENT_DATETIME.plusDays(1),
                    beginEventDateTime = EVENT_BEGIN_EVENT_DATETIME,
                    endEventDateTime = EVENT_BEGIN_EVENT_DATETIME.plusHours(8)
            )
        }
    }
}
