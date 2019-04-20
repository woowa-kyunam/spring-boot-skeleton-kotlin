package com.kyunam.skeleton.config

import com.kyunam.skeleton.common.UserSessionUtil
import com.kyunam.skeleton.dto.account.AccountDto
import com.kyunam.skeleton.service.account.AccountService
import org.springframework.http.HttpHeaders
import org.springframework.util.StringUtils
import java.nio.charset.StandardCharsets
import java.util.*
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

class BasicAuthFilter(private val accountService: AccountService) : Filter {

    private val basicAuthHeader = "Basic "
    private val basicAuthSplitter = ":"

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val authorization = httpRequest.getHeader(HttpHeaders.AUTHORIZATION)
        if (StringUtils.isEmpty(authorization) || !authorization.startsWith(basicAuthHeader)) {
            chain.doFilter(request, response)
            return
        }
        val encodedCredentials = authorization.replace(basicAuthHeader, "")
        val credentials = String(Base64.getDecoder().decode(encodedCredentials), StandardCharsets.UTF_8)
        val values = credentials.split(basicAuthSplitter)
        val account = accountService.login(AccountDto.AccountLoginDto(
                email = values[0],
                password = values[1]
        ))

        val session = httpRequest.session
        UserSessionUtil.setUserSession(session, account)

        chain.doFilter(request, response)
    }
}
