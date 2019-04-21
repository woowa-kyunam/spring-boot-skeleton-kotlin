package com.kyunam.skeleton.controller.event

import com.kyunam.skeleton.common.ControllerTest
import com.kyunam.skeleton.common.CustomMessageUtil
import com.kyunam.skeleton.common.TestObjectCreateUtil
import com.kyunam.skeleton.common.enum.EventStatus
import com.kyunam.skeleton.common.exception.UnAuthorizationException
import com.kyunam.skeleton.dto.event.EventDto
import com.kyunam.skeleton.service.account.AccountService
import com.kyunam.skeleton.service.event.EventService
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional


@Transactional
@ExtendWith(MockitoExtension::class)
internal class EventControllerTest : ControllerTest() {
    @MockBean(name = "accountService")
    private lateinit var accountService: AccountService
    @MockBean(name = "eventService")
    private lateinit var eventService: EventService
    @Autowired
    private lateinit var messageSourceAccessor: MessageSourceAccessor

    @BeforeEach
    fun setUpMockOutput() {
        given(accountService.login(TestObjectCreateUtil.getTestAccountLoginDto())
        ).willReturn(TestObjectCreateUtil.getTestAccount())
        given(eventService.createEvent(TestObjectCreateUtil.getTestEventRequestDto(), accountService.login(TestObjectCreateUtil.getTestAccountLoginDto()))
        ).willReturn(EventDto.EventResponseDto.toPersonRecord(TestObjectCreateUtil.getTestEvent()))
    }

    @Test
    @DisplayName("이벤트 생성 테스트")
    fun `create event api test`() {
        var eventRequestDto = TestObjectCreateUtil.getTestEventRequestDto()

        this.mockMvc.perform(post("/api/v1/events").with(httpBasic(TestObjectCreateUtil.ACCOUNT_EMAIL, TestObjectCreateUtil.ACCOUNT_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(eventRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated)
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("contents").value(eventRequestDto.contents))
                .andExpect(jsonPath("name").value(eventRequestDto.name))
                .andExpect(jsonPath("price").value(eventRequestDto.price))
                .andExpect(jsonPath("address.localAddress").value(eventRequestDto.address.localAddress))
                .andExpect(jsonPath("availableParticipant").value(eventRequestDto.availableParticipant))
                .andExpect(jsonPath("beginEnrollmentDateTime", `is`(eventRequestDto.beginEnrollmentDateTime.toString())))
                .andExpect(jsonPath("endEnrollmentDateTime", `is`(eventRequestDto.endEnrollmentDateTime.toString())))
                .andExpect(jsonPath("beginEventDateTime", `is`(eventRequestDto.beginEventDateTime.toString())))
                .andExpect(jsonPath("endEventDateTime", `is`(eventRequestDto.endEventDateTime.toString())))
                .andExpect(jsonPath("register.email").value(TestObjectCreateUtil.ACCOUNT_EMAIL))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name))
                .andExpect(jsonPath("_links").hasJsonPath())
                .andExpect(jsonPath("_links.self").hasJsonPath())
                .andExpect(jsonPath("_links.events").hasJsonPath())
                .andExpect(jsonPath("_links.update").hasJsonPath())
                .andExpect(jsonPath("_links.profile").hasJsonPath())
    }

    @Test
    @DisplayName("이벤트 생성 시 Validation 예외 발생 테스트")
    fun `create event api validation failure test`() {
        var eventRequestDto = TestObjectCreateUtil.getTestEventRequestDto()
        eventRequestDto.name = ""

        this.mockMvc.perform(post("/api/v1/events").with(httpBasic(TestObjectCreateUtil.ACCOUNT_EMAIL, TestObjectCreateUtil.ACCOUNT_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(eventRequestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$[0].field").hasJsonPath())
                .andExpect(jsonPath("$[0].rejectedValue").hasJsonPath())
                .andExpect(jsonPath("$[0].defaultMessage").hasJsonPath())
                .andExpect(jsonPath("$[0].objectName").hasJsonPath())
    }

    @Test
    @DisplayName("이벤트 업데이트 테스트")
    fun `update event api test`() {
        var eventId = 1L
        var eventRequestDto = TestObjectCreateUtil.getTestEventRequestDto()
        var updateEventRequest: EventDto.EventRequestDto = TestObjectCreateUtil.getTestEventRequestDto()
        val editEventName = "변경된 이벤트 이름"
        updateEventRequest.name = editEventName

        var updatedEventResponse = TestObjectCreateUtil.getTestEvent()
        updatedEventResponse.name = editEventName

        given(eventService.updateEvent(eventId, updateEventRequest, accountService.login(TestObjectCreateUtil.getTestAccountLoginDto()))
        ).willReturn(EventDto.EventResponseDto.toPersonRecord(updatedEventResponse))

        this.mockMvc.perform(patch("/api/v1/events/$eventId").with(httpBasic(TestObjectCreateUtil.ACCOUNT_EMAIL, TestObjectCreateUtil.ACCOUNT_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(updateEventRequest)))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("contents").value(eventRequestDto.contents))
                .andExpect(jsonPath("name").value(editEventName))
                .andExpect(jsonPath("price").value(eventRequestDto.price))
                .andExpect(jsonPath("address.localAddress").value(eventRequestDto.address.localAddress))
                .andExpect(jsonPath("availableParticipant").value(eventRequestDto.availableParticipant))
                .andExpect(jsonPath("beginEnrollmentDateTime", `is`(eventRequestDto.beginEnrollmentDateTime.toString())))
                .andExpect(jsonPath("endEnrollmentDateTime", `is`(eventRequestDto.endEnrollmentDateTime.toString())))
                .andExpect(jsonPath("beginEventDateTime", `is`(eventRequestDto.beginEventDateTime.toString())))
                .andExpect(jsonPath("endEventDateTime", `is`(eventRequestDto.endEventDateTime.toString())))
                .andExpect(jsonPath("register.email").value(TestObjectCreateUtil.ACCOUNT_EMAIL))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name))
                .andExpect(jsonPath("_links").hasJsonPath())
                .andExpect(jsonPath("_links.self").hasJsonPath())
                .andExpect(jsonPath("_links.events").hasJsonPath())
                .andExpect(jsonPath("_links.profile").hasJsonPath())

    }

    @Test
    @DisplayName("이벤트 업데이트 시 권한이 없어 실패하는 경우의 테스트")
    fun `update event api failure test`() {
        var eventId = 1L

        var updateEventRequest: EventDto.EventRequestDto = TestObjectCreateUtil.getTestEventRequestDto()
        val editEventName = "변경된 이벤트 이름"
        updateEventRequest.name = editEventName

        var updatedEventResponse = TestObjectCreateUtil.getTestEvent()
        updatedEventResponse.name = editEventName

        given(eventService.updateEvent(eventId, updateEventRequest, accountService.login(TestObjectCreateUtil.getTestAccountLoginDto()))
        ).willThrow(UnAuthorizationException(messageSourceAccessor.getMessage(CustomMessageUtil.UNAUTHORIZED_EVENT)))

        this.mockMvc.perform(patch("/api/v1/events/$eventId").with(httpBasic(TestObjectCreateUtil.ACCOUNT_EMAIL, TestObjectCreateUtil.ACCOUNT_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(updateEventRequest)))
                .andDo(print())
                .andExpect(status().isForbidden)
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("timestamp").exists())
                .andExpect(jsonPath("errorMessage").exists())
                .andExpect(jsonPath("debugMessage").exists())
    }

    @Test
    @DisplayName("이벤트 삭제 테스트")
    fun `delete event api test`() {
        var eventId = 1L

        var deletedEventResponse = TestObjectCreateUtil.getTestEvent()

        given(eventService.deleteEvent(eventId, accountService.login(TestObjectCreateUtil.getTestAccountLoginDto()))
        ).willReturn(EventDto.EventResponseDto.toPersonRecord(deletedEventResponse))

        this.mockMvc.perform(delete("/api/v1/events/$eventId").with(httpBasic(TestObjectCreateUtil.ACCOUNT_EMAIL, TestObjectCreateUtil.ACCOUNT_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("_links").hasJsonPath())
                .andExpect(jsonPath("_links.self").hasJsonPath())
                .andExpect(jsonPath("_links.events").hasJsonPath())
                .andExpect(jsonPath("_links.profile").hasJsonPath())
    }
}
