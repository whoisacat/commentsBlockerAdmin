package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.User
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.EditUserDTO
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.UserRegistrationDTO

interface UserService {
    fun registerNewUserAccount(userRegistrationDto: UserRegistrationDTO): User
    fun getUsernameFromSecurityContext(): String
    fun getCurrentUser(): User
    fun getEditUserDTO(): EditUserDTO
    fun save(user: User): User
}