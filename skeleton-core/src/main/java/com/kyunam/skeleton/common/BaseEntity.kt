package com.kyunam.skeleton.common

import com.kyunam.skeleton.domain.account.Account
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
    @CreatedBy
    @OneToOne
    protected var createdBy: Account? = null

    @CreatedDate
    protected var createDate: LocalDateTime? = null

    @LastModifiedBy
    @OneToOne
    protected var lastModifiedBy: Account? = null

    @LastModifiedDate
    protected var lastModifiedDate: LocalDateTime? = null

    @Column(nullable = false)
    protected var deleted: Boolean? = false

    protected fun delete() {
        deleted = true
    }
}
