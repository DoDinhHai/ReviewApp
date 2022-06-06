package com.example.reviewapp.data.di

import com.example.reviewapp.data.remote.api.NewsApi
import com.example.reviewapp.data.repository.NewsRepositoryImpl
import com.example.reviewapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun providerNewsRepository(newsApi: NewsApi): NewsRepository {
        return NewsRepositoryImpl(newsApi)
    }
}