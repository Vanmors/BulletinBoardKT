package com.example.demo.entity

import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var roleName: String = ""
)

