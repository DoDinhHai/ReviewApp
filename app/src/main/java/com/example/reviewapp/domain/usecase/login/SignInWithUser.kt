package com.example.reviewapp.domain.usecase.login

import com.example.reviewapp.domain.model.InvalidLoginException
import com.example.reviewapp.domain.model.User
import com.example.reviewapp.domain.repository.LoginRepository

class SignInWithUser(private val loginRepository: LoginRepository) {

    @Throws(InvalidLoginException::class)
    operator fun invoke (user: User){
        if(user.username.isEmpty()){
            throw InvalidLoginException("Vui Lòng Nhập UserName")
        }
        if (user.passWord.isEmpty()){
            throw InvalidLoginException("Vui Lòng Nhập PasWord")
        }

        loginRepository.signInWithUser(user.username,user.passWord)
    }
}