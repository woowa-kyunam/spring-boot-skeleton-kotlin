package com.kyunam.skeleton.service.event

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional


@DisplayName("이벤트 서비스 테스트")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class EventServiceTest {

    @Autowired
    private lateinit var eventService: EventService

    @Test
    @DisplayName("이벤트 생성 테스트")
    fun `Create event test`() {
        eventService.readAllEvent()
    }
}
