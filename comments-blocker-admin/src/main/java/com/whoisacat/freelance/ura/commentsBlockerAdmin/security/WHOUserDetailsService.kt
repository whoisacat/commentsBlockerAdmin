package com.whoisacat.freelance.ura.commentsBlockerAdmin.security

import com.whoisacat.freelance.ura.commentsBlockerAdmin.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class WHOUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
                ?: throw UsernameNotFoundException(username)
        return WHOUserPrincipal(user)
    }
}