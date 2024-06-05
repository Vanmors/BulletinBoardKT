package com.example.demo.service

import com.example.demo.entity.UserAccount
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl(private val user: UserAccount) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        val roles: MutableList<GrantedAuthority> = ArrayList()
        roles.add(SimpleGrantedAuthority("ROLE_${user.role?.roleName}"))
        return roles
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
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
