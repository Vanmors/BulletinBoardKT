package com.example.demo.controller

import com.example.demo.entity.Role
import com.example.demo.repository.RoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/role")
class RoleController @Autowired constructor(
    private val roleRepository: RoleRepository
) {


    @PostMapping
    fun create(@RequestBody role: Role): ResponseEntity<Role> {
        println(role.roleName)
        return ResponseEntity(roleRepository.save(role), HttpStatus.CREATED)
    }
}