package com.whoisacat.freelance.ura.commentsBlockerAdmin.repository

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long?> {
    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

}