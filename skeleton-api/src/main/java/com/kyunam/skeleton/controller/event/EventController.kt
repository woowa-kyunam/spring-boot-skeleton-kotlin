package com.kyunam.skeleton.controller.event

import com.kyunam.skeleton.service.event.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/events")
class EventController @Autowired constructor(
        private val eventService: EventService
) {
    @GetMapping
    fun readEvents(): ResponseEntity<Any> {
        return ResponseEntity.ok(eventService.readAllEvent())
    }
}
