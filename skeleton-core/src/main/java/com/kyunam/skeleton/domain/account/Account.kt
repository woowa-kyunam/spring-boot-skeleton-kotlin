package com.kyunam.skeleton.domain.account

import com.kyunam.skeleton.common.BaseIdEntity
import com.kyunam.skeleton.common.enum.UserRole
import com.kyunam.skeleton.common.exception.AccountValidationException
import javax.persistence.*


@Entity
@Table(name = "account", indexes = [Index(columnList = "account_email", name = "IDX_ACCOUNT_ACCOUNT_EMAIL")])
class Account(email: String, password: String, username: String) : BaseIdEntity() {
    @Column(name = "account_email", nullable = false, unique = true)
    var email: String = ""
        set (email) {
            if (!email.isNullOrEmpty()) field = email else throw AccountValidationException("이메일은 필수 정보입니다.")
        }
    @Column(name = "account_password", nullable = false)
    var password: String = ""
        set (password) {
            if (!password.isNullOrEmpty()) field = password else throw AccountValidationException("패스워드는 필수 정보입니다.")
        }
    @Column(name = "account_username", nullable = false)
    var username: String = ""
        set (username) {
            if (!username.isNullOrEmpty()) field = username else throw AccountValidationException("유저 이름은 필수 정보입니다.")
        }
    @Column(name = "account_role")
    @ElementCollection
    @Enumerated(EnumType.STRING)
    var userRole: MutableSet<UserRole> = mutableSetOf(UserRole.ROLE_USER)
        private set

    fun addUserRole(role: UserRole) {
        this.userRole.add(role)
    }

    init {
        this.email = email
        this.password = password
        this.username = username
    }
}

