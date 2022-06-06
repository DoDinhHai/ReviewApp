package com.example.reviewapp.domain.usecase.login

import com.example.reviewapp.domain.repository.LoginRepository

class SignInWithFacebook(private val loginRepository: LoginRepository) {

    operator fun invoke(){
        loginRepository.signInWithFacebook()
    }
}