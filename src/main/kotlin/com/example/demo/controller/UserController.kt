package com.example.demo.controller

import com.example.demo.dto.UserAccountDTO
import com.example.demo.entity.UserAccount
import com.example.demo.service.UserAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/user")
class UserController @Autowired constructor(

    private val userAccountService: UserAccountService
){


    @PostMapping
    fun create(@RequestBody userAccountDTO: UserAccountDTO): ResponseEntity<UserAccount> {
        return ResponseEntity(userAccountService.create(userAccountDTO), HttpStatus.CREATED)
    }


}