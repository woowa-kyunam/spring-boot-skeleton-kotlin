package com.kyunam.skeleton.service.event

import com.kyunam.skeleton.common.TestObjectCreateUtil
import com.kyunam.skeleton.common.exception.UnAuthorizationException
import com.kyunam.skeleton.domain.account.Account
import com.kyunam.skeleton.repository.account.AccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional


@DisplayName("이벤트 서비스 테스트")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class EventServiceTest {

    @Autowired
    private lateinit var eventService: EventService
    @Autowired
    private lateinit var accountRepository: AccountRepository
    private lateinit var defaultAccount: Account
    private lateinit var anotherAccount: Account

    @BeforeAll
    fun setUp() {
        defaultAccount = accountRepository.save(TestObjectCreateUtil.getTestAccount())
        anotherAccount = accountRepository.save(Account(
                email = "test@naver.com",
                password = "123@$124!@$!@",
                username = "나쁜사람"
        ))
    }

    @Test
    @DisplayName("이벤트 생성 테스트")
    fun `Create event test`() {
        //given
        var eventRequestDto = TestObjectCreateUtil.getTestEventRequestDto()

        //when
        val eventResponseDto = eventService.createEvent(eventRequestDto, defaultAccount)

        //then
        assertThat(eventResponseDto.name).isEqualTo(eventRequestDto.name)
        assertThat(eventResponseDto.register.email).isEqualTo(defaultAccount.email)
    }

    @Test
    @DisplayName("이벤트 업데이트 테스트")
    fun `Update event test`() {
        //given
        var eventRequestDto = TestObjectCreateUtil.getTestEventRequestDto()
        val eventResponseDto = eventService.createEvent(eventRequestDto, defaultAccount)
        val updateEventName = "변경된 이벤트 이름"
        eventRequestDto.name = updateEventName

        //when
        val updatedEventResponseDto = eventService.updateEvent(eventResponseDto.id, eventRequestDto, defaultAccount)

        //then
        assertThat(updatedEventResponseDto.name).isEqualTo(updateEventName)
    }

    @Test
    @DisplayName("이벤트 업데이트 권한 실패 테스트")
    fun `Update event failure test`() {
        //given
        var eventRequestDto = TestObjectCreateUtil.getTestEventRequestDto()
        val eventResponseDto = eventService.createEvent(eventRequestDto, defaultAccount)
        val updateEventName = "변경된 이벤트 이름"
        eventRequestDto.name = updateEventName

        //when
        val exception = Assertions.assertThrows(UnAuthorizationException::class.java) {
            eventService.updateEvent(eventResponseDto.id, eventRequestDto, anotherAccount)
        }

        //then
        assertThat(exception.message).isEqualTo("이벤트 등록자만 수정할 수 있습니다.")
    }
}
