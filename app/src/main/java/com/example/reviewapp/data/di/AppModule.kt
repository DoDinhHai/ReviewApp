package com.example.reviewapp.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.reviewapp.ReviewApplication
import com.example.reviewapp.data.local.db.AppDatabase
import com.example.reviewapp.data.local.db.AppDatabase.Companion.getInstance
import com.example.reviewapp.data.model.ArticlesEntityMapper
import com.example.reviewapp.data.model.MenuEntityMapper
import com.example.reviewapp.data.remote.api.NewsApi
import com.example.reviewapp.data.repository.ArticlesRepositoryImpl
import com.example.reviewapp.data.repository.LoginRepositoryImpl
import com.example.reviewapp.data.repository.MenuRepositoryImpl
import com.example.reviewapp.data.util.Constants.BASE_URL
import com.example.reviewapp.data.util.Constants.DATABASE_NAME
import com.example.reviewapp.domain.repository.ArticlesRepository
import com.example.reviewapp.domain.repository.LoginRepository
import com.example.reviewapp.domain.repository.MenuRepository
import com.example.reviewapp.domain.usecase.articles.*
import com.example.reviewapp.domain.usecase.menu.AddMenu
import com.example.reviewapp.domain.usecase.menu.MenuUseCase
import com.example.reviewapp.domain.usecase.login.LoginUseCase
import com.example.reviewapp.domain.usecase.login.SignInWithFacebook
import com.example.reviewapp.domain.usecase.login.SignInWithGmail
import com.example.reviewapp.domain.usecase.login.SignInWithUser
import com.example.reviewapp.domain.usecase.menu.GetMenu
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(
    SingletonComponent ::class,
    )
object AppModule {
    @Singleton
    @Provides
    fun provideRunningDatabase(
        app: Application
    ) = getInstance(app)

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun providerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providerClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)

        if (true) {
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun providerGSonConverter(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providerRetrofit(gSonConvert: GsonConverterFactory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gSonConvert)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providerService(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideNoteRepository(): LoginRepository {
        return LoginRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: LoginRepository): LoginUseCase {
        return LoginUseCase(
            signInWithUser = SignInWithUser(repository),
            signInWithFacebook = SignInWithFacebook(repository),
            signInWithGmail = SignInWithGmail(repository)
        )
    }

    @Singleton
    @Provides
    fun provideRunDAO(db: AppDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideMenuRepository(db: AppDatabase, mapper: MenuEntityMapper): MenuRepository{
        return MenuRepositoryImpl(db.menuDao(),mapper)
    }

    @Singleton
    @Provides
    fun provideArticlesRepository(db: AppDatabase, mapper: ArticlesEntityMapper): ArticlesRepository{
        return ArticlesRepositoryImpl(db.articlesDao(),mapper)
    }

    @Singleton
    @Provides
    fun provideMenuCase(repository: MenuRepository): MenuUseCase {
        return MenuUseCase(
            addMenu = AddMenu(repository),
            getMenu = GetMenu(repository)
        )
    }

    @Singleton
    @Provides
    fun provideArticlesUseCase(articlesRepository: ArticlesRepository): ArticlesUseCase{
        return ArticlesUseCase(
            addArticles = AddArticles(articlesRepository),
            getArticles = GetArticles(articlesRepository),
            deleteAllArticles = DeleteAllArticles(articlesRepository),
            getArticlePage = GetArticlePage(articlesRepository)
        )
    }

}