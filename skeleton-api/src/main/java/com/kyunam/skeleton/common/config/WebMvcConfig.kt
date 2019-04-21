package com.kyunam.skeleton.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    @Bean
    fun loginUserHandlerMethodArgumentResolver(): LoginUserHandlerMethodArgumentResolver {
        return LoginUserHandlerMethodArgumentResolver()
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginUserHandlerMethodArgumentResolver())
    }
}
