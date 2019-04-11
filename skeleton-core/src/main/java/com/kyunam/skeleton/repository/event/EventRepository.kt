package com.kyunam.skeleton.repository.event

import com.kyunam.skeleton.domain.event.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventRepository : JpaRepository<Event, Long>
