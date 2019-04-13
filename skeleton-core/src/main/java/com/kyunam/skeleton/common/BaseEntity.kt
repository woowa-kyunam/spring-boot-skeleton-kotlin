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
    var createdBy: Account? = null

    @CreatedDate
    var createDate: LocalDateTime? = null

    @LastModifiedBy
    @OneToOne
    var lastModifiedBy: Account? = null

    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null

    @Column(nullable = false)
    var deleted: Boolean? = false

    fun delete() {
        deleted = true
    }
}
