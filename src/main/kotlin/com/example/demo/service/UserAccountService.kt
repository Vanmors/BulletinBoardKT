package com.example.demo.service

import com.example.demo.dto.UserAccountDTO
import com.example.demo.entity.UserAccount
import com.example.demo.repository.UserAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserAccountService(private val userRepository: UserAccountRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
        return UserDetailsImpl(user)
    }

    fun create(userDTO: UserAccountDTO): UserAccount {
        val encodedPassword = BCryptPasswordEncoder().encode(userDTO.password)
        val user = UserAccount(
            username = userDTO.username,
            password = encodedPassword,
            role = userDTO.role
        )
        return userRepository.save(user)
    }

    fun readAll(): List<UserAccount> {
        return userRepository.findAll()
    }

    fun update(user: UserAccount): UserAccount {
        return userRepository.save(user)
    }

    fun delete(id: Long) {
        userRepository.deleteById(id)
    }
}
