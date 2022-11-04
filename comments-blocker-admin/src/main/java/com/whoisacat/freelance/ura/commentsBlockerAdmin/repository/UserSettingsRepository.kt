package com.whoisacat.freelance.ura.commentsBlockerAdmin.repository

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.UserSettings
import org.springframework.data.repository.PagingAndSortingRepository

interface UserSettingsRepository : PagingAndSortingRepository<UserSettings?, Long?> {
    fun findByUserEmail(email: String): UserSettings?
}