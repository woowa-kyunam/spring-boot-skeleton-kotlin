package com.kyunam.skeleton.common

import com.kyunam.skeleton.domain.account.Account
import org.springframework.web.context.request.NativeWebRequest
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

class UserSessionUtil {
    companion object {
        const val USER_SESSION_KEY = "loginUser"
        fun setUserSession(session: HttpSession, account: Account) {
            session.setAttribute(USER_SESSION_KEY, account)
        }

        fun getUserSession(session: HttpSession): Account {
            return session.getAttribute(USER_SESSION_KEY) as Account
        }

        fun getUserSession(request: NativeWebRequest): Account {
            val httpServletRequest = request.nativeRequest as HttpServletRequest
            return getUserSession(httpServletRequest.session)
        }
    }
}
