package com.example.demo.dto

import java.time.LocalDateTime

data class MessageDTO(
    val authorName: String,
    val text: String,
    val createdAll: LocalDateTime
)