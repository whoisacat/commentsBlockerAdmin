package com.whoisacat.freelance.ura.commentsBlockerAdmin.service

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.ROLES
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.Role
import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.User
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.EditUserDTO
import com.whoisacat.freelance.ura.commentsBlockerAdmin.dto.UserRegistrationDTO
import com.whoisacat.freelance.ura.commentsBlockerAdmin.repository.UserRepository
import com.whoisacat.freelance.ura.commentsBlockerAdmin.security.WHOUserPrincipal
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.UserAlreadyExistException
import com.whoisacat.freelance.ura.commentsBlockerAdmin.service.exception.UserNotFoundException
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserServiceImpl(
    private val repository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    @param:Lazy private val userSettingsService: UserSettingsService
) : UserService {

    @Throws(UserAlreadyExistException::class)
    override fun registerNewUserAccount(userRegistrationDto: UserRegistrationDTO): User {
        if (emailExist(userRegistrationDto.email))
            throw UserAlreadyExistException(userRegistrationDto.email)
        val user = User()
        user.firstName = userRegistrationDto.firstName
        user.lastName = userRegistrationDto.lastName
        user.password = passwordEncoder.encode(userRegistrationDto.password)
        user.email = userRegistrationDto.email
        val role = Role()
        role.roleName = ROLES.ROLE_USER
        user.roles.add(role)
        val newUser = repository.save(user)
        userSettingsService.create(user)
        return newUser
    }

    private fun emailExist(email: String): Boolean {
        return repository.existsByEmail(email)
    }

    override fun getUsernameFromSecurityContext(): String {
        val ctxt = SecurityContextHolder.getContext()
        return (ctxt.authentication.principal as WHOUserPrincipal).username
    }

    @Transactional(readOnly = true)
    override fun getCurrentUser(): User {
        return repository.findByEmail(getUsernameFromSecurityContext())
            ?: throw UserNotFoundException()
    }

    @Transactional(readOnly = true)
    override fun getEditUserDTO(): EditUserDTO {
        val user = repository.findByEmail(getUsernameFromSecurityContext())
            ?: throw UserNotFoundException()
        return EditUserDTO(user.firstName, user.lastName, user.email!!, "readonly")
    }

    override fun save(user: User): User {
        return repository.save<User>(user)
    }
}