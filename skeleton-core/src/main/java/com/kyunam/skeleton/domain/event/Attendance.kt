package com.kyunam.skeleton.domain.event

import com.kyunam.skeleton.common.BaseIdEntity
import javax.persistence.Entity
import com.kyunam.skeleton.domain.account.Account
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne


@Entity
class Attendance : BaseIdEntity() {
    @ManyToOne
    @JoinColumn(name = "event_id")
    var attendEvent: Event? = null

    @ManyToOne
    var account: Account? = null

}
