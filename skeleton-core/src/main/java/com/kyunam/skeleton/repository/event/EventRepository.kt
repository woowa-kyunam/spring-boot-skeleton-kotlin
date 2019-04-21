package com.kyunam.skeleton.repository.event

import com.kyunam.skeleton.domain.event.Event
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventRepository : JpaRepository<Event, Long> {
    fun findByIdAndDeletedFalse(id: Long): Optional<Event>
}
