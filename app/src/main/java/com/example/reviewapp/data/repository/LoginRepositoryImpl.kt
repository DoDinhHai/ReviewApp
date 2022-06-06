package com.example.reviewapp.data.repository

import com.example.reviewapp.domain.model.User
import com.example.reviewapp.domain.repository.LoginRepository
import io.reactivex.Completable

class LoginRepositoryImpl(): LoginRepository {
    override fun signInWithUser(userName: String, password: String): Completable {
        TODO("Not yet implemented")
    }

    override fun signInWithFacebook() {
        TODO("Not yet implemented")
    }

    override fun signInWithGmail() {
        TODO("Not yet implemented")
    }

    override fun saveUser(user: User) {
        TODO("Not yet implemented")
    }
}