package com.kyunam.skeleton.service.event

import com.kyunam.skeleton.domain.event.Event
import com.kyunam.skeleton.repository.event.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.Integer.parseInt

@Service
@Transactional(readOnly = true)
class EventService (
        private val eventRepository: EventRepository

) {
    fun readAllEvent(): List<Event> {
        return eventRepository.findAll()
    }
}
