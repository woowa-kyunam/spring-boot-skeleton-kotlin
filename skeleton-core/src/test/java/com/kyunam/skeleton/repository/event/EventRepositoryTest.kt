package com.kyunam.skeleton.repository.event

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@DisplayName("이벤트 Repository Test")
class EventRepositoryTest @Autowired constructor(
        private val eventRepository: EventRepository
) {
    @Test
    @DisplayName("이벤트 전체 조회 테스트")
    fun `findAll event test`() {
        assertThat(eventRepository.findAll()).isEmpty()
    }
}
