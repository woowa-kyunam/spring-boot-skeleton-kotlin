package com.kyunam.skeleton.dto.event

import com.fasterxml.jackson.annotation.JsonFormat
import com.kyunam.skeleton.common.enum.EventStatus
import com.kyunam.skeleton.domain.event.Address
import com.kyunam.skeleton.domain.event.Event
import com.kyunam.skeleton.dto.account.AccountDto
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class EventDto {
    data class EventRequestDto(
            @field:NotNull
            var name: String,
            @field:NotNull
            var contents: String,
            @field:Min(Event.MIN_PRICE.toLong())
            @field:Max(Event.MAX_PRICE.toLong())
            var price: Int,
            var address: Address,
            @field:NotNull
            @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
            var beginEnrollmentDateTime: LocalDateTime,
            @field:NotNull
            @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
            var endEnrollmentDateTime: LocalDateTime,
            @field:NotNull
            @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
            var beginEventDateTime: LocalDateTime,
            @field:NotNull
            @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
            var endEventDateTime: LocalDateTime,
            @field:Min(Event.MIN_PARTICIPANT.toLong())
            @field:Max(Event.MAX_PARTICIPANT.toLong())
            var availableParticipant: Int
    )

    data class EventResponseDto(
            var id: Long,
            var name: String?,
            var contents: String?,
            var price: Int?,
            var address: Address?,
            @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
            var beginEnrollmentDateTime: LocalDateTime,
            @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
            var endEnrollmentDateTime: LocalDateTime,
            @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
            var beginEventDateTime: LocalDateTime,
            @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
            var endEventDateTime: LocalDateTime,
            var availableParticipant: Int?,
            var register: AccountDto.AccountResponseDto,
            var eventStatus: EventStatus
    ) {
        companion object {
            fun toPersonRecord(event: Event) = EventResponseDto(
                    id = event.id!!,
                    name = event.name,
                    contents = event.contents,
                    price = event.price,
                    address = event.address,
                    beginEnrollmentDateTime = event.beginEnrollmentDateTime,
                    endEnrollmentDateTime = event.endEnrollmentDateTime,
                    endEventDateTime = event.endEventDateTime,
                    beginEventDateTime = event.beginEventDateTime,
                    availableParticipant = event.availableParticipant,
                    register = AccountDto.AccountResponseDto.toAccountResponseDto(event.register),
                    eventStatus = event.eventStatus
            )
        }
    }
}
