package com.example.reviewapp.domain.repository

import com.example.reviewapp.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository : Repository {
    fun getUser(id: String, fromServer: Boolean): Single<User>

    fun signIn(userName: String, password: String): Completable

    fun saveUser(user: User)
}