package com.example.demo.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val title: String,

    @OneToMany(mappedBy = "topic", cascade = [CascadeType.ALL])
    @JsonIgnore
    val messages: List<Message> = mutableListOf()
)