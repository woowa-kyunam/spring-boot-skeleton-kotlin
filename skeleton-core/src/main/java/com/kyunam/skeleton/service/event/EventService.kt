package com.kyunam.skeleton.service.event

import com.kyunam.skeleton.common.CustomMessageUtil
import com.kyunam.skeleton.common.exception.EventValidationException
import com.kyunam.skeleton.domain.account.Account
import com.kyunam.skeleton.domain.event.Event
import com.kyunam.skeleton.dto.event.EventDto
import com.kyunam.skeleton.repository.event.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.Integer.parseInt

@Service
@Transactional(readOnly = true)
class EventService(
        private val messageSourceAccessor: MessageSourceAccessor,
        private val eventRepository: EventRepository

) {
    fun readAllEvent(): List<Event> {
        return eventRepository.findAll()
    }

    fun getEvent(id: Long): Event {
        return eventRepository.findById(id).orElseThrow { EventValidationException(messageSourceAccessor.getMessage(CustomMessageUtil.EVENT_NOTFOUND)) }
    }

    @Transactional
    fun createEvent(eventRequestDto: EventDto.EventRequestDto, register: Account): EventDto.EventResponseDto {
        return EventDto.EventResponseDto.toPersonRecord(eventRepository.save(Event(
                name = eventRequestDto.name,
                contents = eventRequestDto.contents,
                address = eventRequestDto.address,
                price = eventRequestDto.price,
                register = register,
                availableParticipant = eventRequestDto.availableParticipant,
                beginEnrollmentDateTime = eventRequestDto.beginEnrollmentDateTime,
                endEnrollmentDateTime = eventRequestDto.endEnrollmentDateTime,
                beginEventDateTime = eventRequestDto.beginEventDateTime,
                endEventDateTime = eventRequestDto.endEventDateTime
        )))
    }

    @Transactional
    fun updateEvent(id: Long, eventRequestDto: EventDto.EventRequestDto, account: Account): EventDto.EventResponseDto {
        var savedEvent = getEvent(id)
        savedEvent.updateEvent(account, eventRequestDto)
        return EventDto.EventResponseDto.toPersonRecord(savedEvent)
    }
}
