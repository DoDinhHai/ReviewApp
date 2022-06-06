package com.example.reviewapp.domain.model

data class User(
    val id: String,
    val name: String,
    val username: String,
    val passWord: String,
    val email: String,
    val phone: String,
    val address: String
) : Model()

class InvalidLoginException(message: String): Exception(message)
