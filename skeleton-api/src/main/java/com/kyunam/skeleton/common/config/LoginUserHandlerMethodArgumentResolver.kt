package com.kyunam.skeleton.common.config

import com.kyunam.skeleton.common.UserSessionUtil
import com.kyunam.skeleton.common.annotation.LoginUser
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

class LoginUserHandlerMethodArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginUser::class.java)
    }

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        return webRequest.getAttribute(UserSessionUtil.USER_SESSION_KEY, WebRequest.SCOPE_SESSION)
    }
}
