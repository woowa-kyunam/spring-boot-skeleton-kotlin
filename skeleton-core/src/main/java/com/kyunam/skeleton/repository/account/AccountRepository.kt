package com.kyunam.skeleton.repository.account

import com.kyunam.skeleton.domain.account.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long>
