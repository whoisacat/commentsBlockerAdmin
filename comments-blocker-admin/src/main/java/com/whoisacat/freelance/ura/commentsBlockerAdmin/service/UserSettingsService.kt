package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.User
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.UserSettings
import com.whoisacat.freelance.ura.commentsBlockerAdmin.repository.UserSettingsRepository
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.UserSettingsNotFound
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserSettingsService(private val repository: UserSettingsRepository,
    private val userService: UserService) {

    @Transactional(readOnly = true)
    fun getUserSettings(): UserSettings {
        val username = userService.getUsernameFromSecurityContext()
        return repository.findByUserEmail(username) ?: throw UserSettingsNotFound()
    }

    @Transactional
    fun setRowsPerPage(rowsPerPage: Int = 10) {
        val username = userService.getUsernameFromSecurityContext()
        val userSettings = repository.findByUserEmail(username)
            ?: throw UserSettingsNotFound()
        userSettings.rowsPerPage = rowsPerPage
        repository.save(userSettings)
    }

    @Transactional
    fun create(user: User?) {
        val userSettings = UserSettings()
        userSettings.user = user
        userSettings.rowsPerPage = INITIAL_ROWS_PER_PAGE_QUANTITY
        repository.save(userSettings)
    }

    companion object {
        const val INITIAL_ROWS_PER_PAGE_QUANTITY = 3
    }
}