package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.User
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.UserAlreadyExistException
import com.whoisacat.freelance.ura.dto.EditUserDTO
import com.whoisacat.freelance.ura.dto.UserRegistrationDTO

interface UserService {
    @Throws(UserAlreadyExistException::class)
    fun registerNewUserAccount(userRegistrationDto: UserRegistrationDTO): User
    fun getUsernameFromSecurityContext(): String
    fun getCurrentUser(): User
    fun getEditUserDTO(): EditUserDTO
    fun save(user: User): User
}