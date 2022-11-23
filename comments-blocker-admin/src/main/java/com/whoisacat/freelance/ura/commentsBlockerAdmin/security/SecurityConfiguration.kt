package com.whoisacat.freelance.ura.commentsBlockerAdmin.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@EnableWebSecurity
class SecurityConfiguration(userDetailsService: WHOUserDetailsService) : WebSecurityConfigurerAdapter() {
    private val userDetailsService: UserDetailsService

    init {
        this.userDetailsService = userDetailsService
    }

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/auth")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and().authorizeRequests().antMatchers("/up", "/swagger-ui.html", "/swagger-ui/**")
            .permitAll()
            .and().authorizeRequests().antMatchers("/user/registration", "/monitoring/**")
            .hasRole("ADMIN")
            .and().authorizeRequests().antMatchers("/**").hasRole("USER")
            .and().formLogin()
            .and().logout().logoutRequestMatcher(AntPathRequestMatcher("/logout"))
            .and().rememberMe().key("whoChatSecret").tokenValiditySeconds(ONE_DAY_SECONDS)
    }

    @Autowired
    @Throws(Exception::class)
    public override fun configure(builder: AuthenticationManagerBuilder) {
        builder.userDetailsService(userDetailsService)
    }

    @Bean
    fun daoAuthenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setPasswordEncoder(passwordEncoder())
        provider.setUserDetailsService(userDetailsService)
        return provider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(13)
    }

    companion object {
        const val ONE_DAY_SECONDS = 60 * 60 * 24
    }
}