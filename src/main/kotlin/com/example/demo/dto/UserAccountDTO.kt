package com.example.demo.dto

import com.example.demo.entity.Role

data class UserAccountDTO(
    val username: String,
    val password: String,
    val role: Role
)
