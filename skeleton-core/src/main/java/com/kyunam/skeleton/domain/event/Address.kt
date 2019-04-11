package com.kyunam.skeleton.domain.event

import com.kyunam.skeleton.common.exception.EventValidationException
import javax.persistence.Column
import javax.persistence.Embeddable



@Embeddable
class Address {
    constructor(localAddress: String?, roadAddress: String?, postalCode: String?) {
        this.localAddress = localAddress
        this.roadAddress = roadAddress
        this.postalCode = postalCode
    }

    private constructor(isOnline: Boolean) {
        this.isOnline = isOnline
    }

    fun isEmpty(): Boolean {
        if (this.isOnline) return false
        if (this.localAddress.isNullOrEmpty()) return true
        if (this.roadAddress.isNullOrEmpty()) return true
        if (this.postalCode.isNullOrEmpty()) return true
        return false
    }

    var localAddress: String? = null
        set (localAddress) {
            if (!localAddress.isNullOrEmpty()) field = localAddress else throw EventValidationException("지번 주소는 필수 정보입니다.")
        }
    var roadAddress: String? = null
        set(roadAddress) {
            if(!roadAddress.isNullOrEmpty()) field = roadAddress else throw EventValidationException("도로명 주소는 필수 정보입니다.")
        }
    var postalCode: String? = null
        set(postalCode) {
            if(!postalCode.isNullOrEmpty()) field = postalCode else throw EventValidationException("우편번호는 필수 정보입니다.")
        }
    @Column(nullable = false)
    var isOnline: Boolean = false

    companion object {
        fun ofOnlineEvent(): Address {
            return Address(true)
        }
    }
}
