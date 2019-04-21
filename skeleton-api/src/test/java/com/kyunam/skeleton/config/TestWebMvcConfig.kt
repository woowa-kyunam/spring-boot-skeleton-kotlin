package com.kyunam.skeleton.config

import com.kyunam.skeleton.service.account.AccountService
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class TestWebMvcConfig(
        private val accountService: AccountService
) : WebMvcConfigurer {
    @Bean
    internal fun basicAuthFilterRegistrationBean(): FilterRegistrationBean<BasicAuthFilter> {
        val registrationBean = FilterRegistrationBean<BasicAuthFilter>()
        registrationBean.filter = basicAuthFilter()
        registrationBean.addUrlPatterns("/*")
        registrationBean.order = Ordered.HIGHEST_PRECEDENCE
        return registrationBean
    }

    @Bean
    fun basicAuthFilter(): BasicAuthFilter {
        return BasicAuthFilter(accountService)
    }
}
