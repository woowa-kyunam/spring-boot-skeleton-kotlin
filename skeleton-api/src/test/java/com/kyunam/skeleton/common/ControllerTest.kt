package com.kyunam.skeleton.common

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Disabled
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
class ControllerTest{
    @Autowired
    protected lateinit var mockMvc: MockMvc
    @Autowired
    protected lateinit var objectMapper: ObjectMapper
}
