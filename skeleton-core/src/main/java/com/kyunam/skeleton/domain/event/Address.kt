package com.kyunam.skeleton.domain.event

import com.kyunam.skeleton.common.exception.EventValidationException
import javax.persistence.Embeddable



@Embeddable
class Address {
    constructor(localAddress: String, roadAddress: String, postalCode: String) {
        this.localAddress = localAddress
        this.roadAddress = roadAddress
        this.postalCode = postalCode
    }

    fun isEmpty(): Boolean {
        if (this.localAddress.isNullOrEmpty()) return true
        if (this.roadAddress.isNullOrEmpty()) return true
        if (this.postalCode.isNullOrEmpty()) return true
        return false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Address) return false

        if (localAddress != other.localAddress) return false
        if (roadAddress != other.roadAddress) return false
        if (postalCode != other.postalCode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = localAddress.hashCode()
        result = 31 * result + roadAddress.hashCode()
        result = 31 * result + postalCode.hashCode()
        return result
    }

    var localAddress: String = ""
        set (localAddress) {
            if (!localAddress.isNullOrEmpty()) field = localAddress else throw EventValidationException("지번 주소는 필수 정보입니다.")
        }
    var roadAddress: String = ""
        set(roadAddress) {
            if(!roadAddress.isNullOrEmpty()) field = roadAddress else throw EventValidationException("도로명 주소는 필수 정보입니다.")
        }
    var postalCode: String = ""
        set(postalCode) {
            if(!postalCode.isNullOrEmpty()) field = postalCode else throw EventValidationException("우편번호는 필수 정보입니다.")
        }

}
