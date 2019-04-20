package com.kyunam.skeleton.controller.event

import com.kyunam.skeleton.common.ControllerTest
import com.kyunam.skeleton.common.TestObjectCreateUtil
import com.kyunam.skeleton.common.enum.EventStatus
import com.kyunam.skeleton.dto.event.EventDto
import com.kyunam.skeleton.service.account.AccountService
import com.kyunam.skeleton.service.event.EventService
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status



@Transactional
@ExtendWith(MockitoExtension::class)
internal class EventControllerTest : ControllerTest() {
    @MockBean(name = "accountService")
    private lateinit var accountService: AccountService
    @MockBean(name = "eventService")
    private lateinit var eventService: EventService
    private val eventRequestDto = TestObjectCreateUtil.getTestEventRequestDto()

    @BeforeEach
    fun setUpMockOutput() {
        given(accountService.login(TestObjectCreateUtil.getTestAccountLoginDto())
        ).willReturn(TestObjectCreateUtil.getTestAccount())
        given(eventService.createEvent(eventRequestDto, accountService.login(TestObjectCreateUtil.getTestAccountLoginDto()))
        ).willReturn(EventDto.EventResponseDto.toPersonRecord(TestObjectCreateUtil.getTestEvent()))
    }

    @Test
    fun createEvent() {
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
}
