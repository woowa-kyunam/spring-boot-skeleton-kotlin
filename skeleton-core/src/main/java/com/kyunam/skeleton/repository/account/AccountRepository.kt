package com.kyunam.skeleton.repository.account

import com.kyunam.skeleton.domain.account.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AccountRepository : JpaRepository<Account, Long> {
    fun findByEmail(email: String): Optional<Account>
}
