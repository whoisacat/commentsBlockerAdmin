package com.whoisacat.freelance.ura.commentsBlockerAdmin.security

import com.whoisacat.freelance.ura.commentsBlockerAdmin.domain.User
import com.whoisacat.freelance.ura.commentsBlockerAdmin.security.exception.WHOSecurityException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class WHOUserPrincipal(private val user: User) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return user.authorities
    }

    override fun getPassword(): String {
        return user.password ?: throw WHOSecurityException("passwordIsNull")
    }

    override fun getUsername(): String {
        return user.email ?: throw WHOSecurityException("email")
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}