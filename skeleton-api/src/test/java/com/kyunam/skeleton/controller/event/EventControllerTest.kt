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
import org.springframework.hateoas.MediaTypes
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.*
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
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
                .andDo(document("create-event",
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        requestFields(
                                fieldWithPath("name").description("이벤트 명"),
                                fieldWithPath("contents").description("이벤트 내용"),
                                fieldWithPath("address").description("주소").optional(),
                                fieldWithPath("address.localAddress").description("지번 주소"),
                                fieldWithPath("address.roadAddress").description("도로명 주소"),
                                fieldWithPath("address.postalCode").description(" 우편 번호"),
                                fieldWithPath("price").description("이벤트 가격"),
                                fieldWithPath("availableParticipant").description("참석 가능 인원"),
                                fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작시간"),
                                fieldWithPath("endEnrollmentDateTime").description("이벤트 등록 종료시간"),
                                fieldWithPath("beginEventDateTime").description("이벤트 시작시간"),
                                fieldWithPath("endEventDateTime").description("이벤트 종료시간")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description(HttpHeaders.LOCATION),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        responseFields(
                                fieldWithPath("name").description("이벤트 명"),
                                fieldWithPath("contents").description("이벤트 내용"),
                                fieldWithPath("address.localAddress").description("지번 주소"),
                                fieldWithPath("address.roadAddress").description("도로명 주소"),
                                fieldWithPath("address.postalCode").description(" 우편 번호"),
                                fieldWithPath("price").description("이벤트 가격"),
                                fieldWithPath("availableParticipant").description("참석 가능 인원"),
                                fieldWithPath("register.username").description("이벤트 등록자 이름"),
                                fieldWithPath("eventStatus").description("이벤트 상태"),
                                fieldWithPath("availableParticipant").description("참석 가능 인원"),
                                fieldWithPath("beginEnrollmentDateTime").description("이벤트 등록 시작시간"),
                                fieldWithPath("endEnrollmentDateTime").description("이벤트 등록 종료시간"),
                                fieldWithPath("beginEventDateTime").description("이벤트 시작시간"),
                                fieldWithPath("endEventDateTime").description("이벤트 종료시간"),
                                fieldWithPath("register.email").description("이벤트 등록자 email"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.events.href").description("link to list"),
                                fieldWithPath("_links.update.href").description("link to update"),
                                fieldWithPath("_links.delete.href").description("link to delete"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
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

        this.mockMvc.perform(patch("/api/v1/events/{id}", eventId).with(httpBasic(TestObjectCreateUtil.ACCOUNT_EMAIL, TestObjectCreateUtil.ACCOUNT_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(updateEventRequest)))
                .andDo(print())
                .andExpect(status().isOk)
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
                .andDo(document("update-event",
                        pathParameters(
                                parameterWithName("id").description("수정할 이벤트 Id")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        requestFields(
                                fieldWithPath("name").description("수정할 이벤트 명"),
                                fieldWithPath("contents").description("수정할 이벤트 내용"),
                                fieldWithPath("address").description("수정할 주소").optional(),
                                fieldWithPath("address.localAddress").description("수정할 지번 주소"),
                                fieldWithPath("address.roadAddress").description("수정할 도로명 주소"),
                                fieldWithPath("address.postalCode").description(" 수정할 우편 번호"),
                                fieldWithPath("price").description("수정할 이벤트 가격"),
                                fieldWithPath("availableParticipant").description("수정할 참석 가능 인원"),
                                fieldWithPath("beginEnrollmentDateTime").description("수정할 이벤트 등록 시작 시간"),
                                fieldWithPath("endEnrollmentDateTime").description("수정할 이벤트 등록 종료시간"),
                                fieldWithPath("beginEventDateTime").description("수정할 이벤트 시작 시간"),
                                fieldWithPath("endEventDateTime").description("수정할 이벤트 종료시간")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        responseFields(
                                fieldWithPath("name").description("수정된 이벤트 명"),
                                fieldWithPath("contents").description("수정된 이벤트 내용"),
                                fieldWithPath("address.localAddress").description("수정된 지번 주소"),
                                fieldWithPath("address.roadAddress").description("수정된 도로명 주소"),
                                fieldWithPath("address.postalCode").description("수정된 우편 번호"),
                                fieldWithPath("price").description("수정된 이벤트 가격"),
                                fieldWithPath("availableParticipant").description("수정된 참석 가능 인원"),
                                fieldWithPath("register.username").description("수정된 이벤트 등록자 이름"),
                                fieldWithPath("eventStatus").description("수정 이후의 이벤트 상태"),
                                fieldWithPath("availableParticipant").description("수정된 참석 가능 인원"),
                                fieldWithPath("beginEnrollmentDateTime").description("수정된 이벤트 등록 시작시간"),
                                fieldWithPath("endEnrollmentDateTime").description("수정된 이벤트 등록 종료시간"),
                                fieldWithPath("beginEventDateTime").description("수정된 이벤트 시작시간"),
                                fieldWithPath("endEventDateTime").description("수정된 이벤트 종료시간"),
                                fieldWithPath("register.email").description("수정된 이벤트 등록자 email"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.events.href").description("link to list"),
                                fieldWithPath("_links.delete.href").description("link to delete"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))

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

        this.mockMvc.perform(delete("/api/v1/events/{id}", eventId).with(httpBasic(TestObjectCreateUtil.ACCOUNT_EMAIL, TestObjectCreateUtil.ACCOUNT_PASSWORD)))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath("content").exists())
                .andExpect(jsonPath("_links").hasJsonPath())
                .andExpect(jsonPath("_links.events").hasJsonPath())
                .andExpect(jsonPath("_links.profile").hasJsonPath())
                .andDo(document("delete-event",
                        pathParameters(
                                parameterWithName("id").description("이벤트 Id")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description(MediaTypes.HAL_JSON_UTF8_VALUE)
                        ),
                        responseFields(
                                fieldWithPath("content").description("삭제된 이벤트 id"),
                                fieldWithPath("_links.events.href").description("link to list"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                ))
    }
}
