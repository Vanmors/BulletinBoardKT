package com.example.demo.config

import com.example.demo.service.UserAccountService
import org.apache.catalina.webresources.TomcatURLStreamHandlerFactory.disable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.Customizer.withDefaults

@EnableWebSecurity
@Configuration
class SecurityConfig(
    @Autowired private val userService: UserAccountService
) {

    fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(daoAuthenticationProvider())
    }

    @Bean
    fun daoAuthenticationProvider(): DaoAuthenticationProvider {
        val daoAuthenticationProvider = DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(userService)
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        return daoAuthenticationProvider
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors().and().csrf().disable()
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers(HttpMethod.DELETE, "/api/topic/{topicId}").hasRole("ADMIN")
                    .requestMatchers("/api/user").permitAll()
                    .requestMatchers("/api/role").permitAll()
                    .anyRequest().authenticated()
            }
            .httpBasic(withDefaults())
        return http.build()
    }
}
