package com.whoisacat.freelance.ura.commentsBlockerAdmin.security

import com.whoisacat.freelance.ura.commentsBlockerAdmin.config.ApiAttributes
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailsService: WHOUserDetailsService,
    private val logoutHandler: WHOLogoutHandler,
    private val accessDeniedExceptionFilter: AccessDeniedExceptionFilter,
    private val apiAttributes: ApiAttributes
)
    : WebSecurityConfigurerAdapter() {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @Throws(Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/auth")
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and().authorizeRequests().antMatchers("login","/monitoring/prometheus/**", "/up", "/api/swagger-ui.html", "/api/swagger-ui/**")
            .permitAll()
            .and().authorizeRequests().antMatchers("/api/user/registration", "/api/monitoring/**")
            .hasRole("ADMIN")
            .and().authorizeRequests().antMatchers("/**").hasRole("USER")
            .and().formLogin().defaultSuccessUrl(
                "${apiAttributes.frontProtocol}://${apiAttributes.frontHost}:${apiAttributes.frontPort}",
                true)
            .failureHandler { request, response, authException ->
                log.debug("failureHandler")
                response.sendError(HttpStatus.UNAUTHORIZED.value())
            }
            .successHandler { request, response, authException ->
                response.sendRedirect(
                    "${apiAttributes.frontProtocol}://${apiAttributes.frontHost}:${apiAttributes.frontPort}")
            }
            .and()
            .logout().logoutRequestMatcher(AntPathRequestMatcher("/logout"))
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler { request, response, authException ->
                response.sendRedirect("${apiAttributes.frontProtocol}://${apiAttributes.frontHost}:" +
                        "${apiAttributes.frontPort}/login")
            }
            .and()
            .exceptionHandling()
            .and()
            .addFilterAfter(accessDeniedExceptionFilter, ExceptionTranslationFilter::class.java)
            .rememberMe().key("whoBlockerSecret").tokenValiditySeconds(ONE_DAY_SECONDS)
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