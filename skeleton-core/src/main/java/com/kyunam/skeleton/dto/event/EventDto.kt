package com.kyunam.skeleton.dto.event

import com.fasterxml.jackson.annotation.JsonFormat
import com.kyunam.skeleton.common.enum.EventStatus
import com.kyunam.skeleton.domain.event.Address
import com.kyunam.skeleton.domain.event.Event
import com.kyunam.skeleton.dto.account.AccountDto
import java.time.LocalDateTime
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class EventDto {
    data class EventRequestDto(
            @field:NotNull
            val name: String,
            @field:NotNull
            val contents: String,
            @field:Min(Event.MIN_PRICE.toLong())
            @field:Max(Event.MAX_PRICE.toLong())
            val price: Int,
            val address: Address,
            @field:NotNull
            val beginEnrollmentDateTime: LocalDateTime,
            @field:NotNull
            val endEnrollmentDateTime: LocalDateTime,
            @field:NotNull
            val beginEventDateTime: LocalDateTime,
            @field:NotNull
            val endEventDateTime: LocalDateTime,
            @field:Min(Event.MIN_PARTICIPANT.toLong())
            @field:Max(Event.MAX_PARTICIPANT.toLong())
            val availableParticipant: Int
    )

    data class EventResponseDto(
            val id: Long,
            val name: String?,
            val contents: String?,
            val price: Int?,
            val address: Address?,
            @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            val beginEnrollmentDateTime: LocalDateTime,
            @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            val endEnrollmentDateTime: LocalDateTime,
            @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            val beginEventDateTime: LocalDateTime,
            @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            val endEventDateTime: LocalDateTime,
            val availableParticipant: Int?,
            val register: AccountDto.AccountResponseDto,
            val eventStatus: EventStatus
    ) {
        companion object {
            fun toPersonRecord(event: Event) = EventResponseDto(
                    id = event.id,
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
