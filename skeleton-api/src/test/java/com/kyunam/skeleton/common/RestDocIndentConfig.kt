package com.kyunam.skeleton.common

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint


@Configuration
class RestDocIndentConfig {

    @Bean
    fun customizer(): RestDocsMockMvcConfigurationCustomizer {
        return RestDocsMockMvcConfigurationCustomizer { configurer ->
            configurer.operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())
        }
    }
}
