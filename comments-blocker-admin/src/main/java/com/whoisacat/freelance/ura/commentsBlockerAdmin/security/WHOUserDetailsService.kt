package com.whoisacat.freelance.ura.commentsBlockerAdmin.security

import com.whoisacat.freelance.ura.commentsBlockerAdmin.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WHOUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
                ?: throw UsernameNotFoundException(username)
        return WHOUserPrincipal(user)
    }
}