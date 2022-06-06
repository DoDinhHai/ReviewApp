package com.example.reviewapp.domain.repository

import com.example.reviewapp.domain.model.User
import io.reactivex.Completable

interface LoginRepository : Repository{

    fun signInWithUser(userName: String, password: String): Completable

    fun signInWithFacebook()

    fun signInWithGmail()

    fun saveUser(user: User)
}