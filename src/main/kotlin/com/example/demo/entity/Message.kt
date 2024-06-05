package com.example.demo.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Message(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val authorName: String,

    var text: String,

    val createdAt: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "topic_id")
    val topic: Topic
)
