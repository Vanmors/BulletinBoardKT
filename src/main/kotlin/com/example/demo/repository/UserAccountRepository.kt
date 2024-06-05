package com.example.demo.repository

import com.example.demo.entity.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccountRepository : JpaRepository<UserAccount, Long> {
    fun findByUsername(name: String): UserAccount
}