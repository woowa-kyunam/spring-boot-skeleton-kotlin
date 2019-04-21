package com.kyunam.skeleton.common

import com.kyunam.skeleton.common.exception.UnAuthorizationException

class CustomMessageUtil {
    companion object {
        const val EVENT_NOTFOUND = "NotFound.event"
        const val INVALID_ACCOUNT = "UnAuthentication.invalid.account"
        const val ACCOUNT_NOTFOUND = "NotFound.account"
        const val UNAUTHORIZED_EVENT = "UnAuthorized.event"
    }
}
