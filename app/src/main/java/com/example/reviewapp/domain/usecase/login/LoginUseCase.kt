package com.example.reviewapp.domain.usecase.login

data class LoginUseCase(
    val signInWithUser: SignInWithUser,
    val signInWithFacebook: SignInWithFacebook,
    val signInWithGmail: SignInWithGmail
)
