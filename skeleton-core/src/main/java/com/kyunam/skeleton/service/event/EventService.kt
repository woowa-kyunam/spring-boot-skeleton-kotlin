package com.kyunam.skeleton.service.event

import com.kyunam.skeleton.domain.event.Event
import com.kyunam.skeleton.repository.event.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService (private val eventRepository: EventRepository) {
    fun readAllEvent(): List<Event> {
        return eventRepository.findAll()
    }
}
