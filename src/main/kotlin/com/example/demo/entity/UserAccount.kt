package com.example.demo.entity

import jakarta.persistence.*

@Entity
data class UserAccount(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,

    var username: String = "",
    var password: String = "",

    @ManyToOne
    @JoinColumn(name = "fk_role_id")
    var role: Role? = null
)

