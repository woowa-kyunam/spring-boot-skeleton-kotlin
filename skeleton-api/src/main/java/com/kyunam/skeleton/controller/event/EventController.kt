package com.kyunam.skeleton.controller.event

import com.kyunam.skeleton.common.annotation.LoginUser
import com.kyunam.skeleton.domain.account.Account
import com.kyunam.skeleton.dto.event.EventDto
import com.kyunam.skeleton.service.event.EventService
import org.springframework.hateoas.Link
import org.springframework.hateoas.Resource
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/events")
class EventController(
        private val eventService: EventService
) {
    @GetMapping
    fun readEvents(): ResponseEntity<Any> {
        return ResponseEntity.ok(eventService.readAllEvent())
    }

    @PostMapping
    fun createEvent(@RequestBody @Valid eventRequestDto: EventDto.EventRequestDto, errors: Errors, @LoginUser loginUser: Account): ResponseEntity<*> {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors)
        }
        val savedEventResponse = eventService.createEvent(eventRequestDto, loginUser)
        val resource = getDefaultEventResource(savedEventResponse)
        resource.add(linkTo(EventController::class.java).slash(savedEventResponse.id).withRel("update"))
        resource.add(linkTo(EventController::class.java).slash(savedEventResponse.id).withRel("delete"))
        resource.add(Link("/docs/index.html#resources-events-create", "profile"))
        val uri = linkTo(EventController::class.java).slash(savedEventResponse.id).toUri()
        return ResponseEntity.created(uri).body(resource)
    }

    @PatchMapping("/{id}")
    fun updateEvent(@PathVariable id: Long, @RequestBody @Valid eventRequestDto: EventDto.EventRequestDto, errors: Errors, @LoginUser loginUser: Account): ResponseEntity<*> {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors)
        }
        val updatedEventResponse = eventService.updateEvent(id, eventRequestDto, loginUser)
        val resource = getDefaultEventResource(updatedEventResponse)
        resource.add(linkTo(EventController::class.java).slash(updatedEventResponse.id).withRel("delete"))
        resource.add(Link("/docs/index.html#resources-events-update", "profile"))
        return ResponseEntity.ok(resource)
    }

    private fun getDefaultEventResource(event: EventDto.EventResponseDto): Resource<EventDto.EventResponseDto> {
        val resource = Resource<EventDto.EventResponseDto>(event)
        resource.add(linkTo(EventController::class.java).slash(event.id).withSelfRel())
        resource.add(linkTo(EventController::class.java).withRel("events"))
        return resource
    }
}
