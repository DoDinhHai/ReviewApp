package com.example.reviewapp.data.local.db

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.reviewapp.data.local.db.dao.ArticlesDao
import com.example.reviewapp.data.local.db.dao.MenuDao
import com.example.reviewapp.data.local.db.dao.UserDao
import com.example.reviewapp.data.model.ArticlesEntity
import com.example.reviewapp.data.model.MenuEntity
import com.example.reviewapp.data.model.UserEntity
import com.example.reviewapp.data.util.Constants.DATABASE_NAME


@Database(entities = [UserEntity::class, MenuEntity::class, ArticlesEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun menuDao(): MenuDao
    abstract fun articlesDao(): ArticlesDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(app: Application) =
            INSTANCE ?: synchronized(AppDatabase::class.java) {
                INSTANCE ?: Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build().also {
                        INSTANCE = it
                    }
            }
    }

    var MIGRATION_1_TO_2: Migration = object : Migration(1, 2) {
        override fun migrate(@NonNull database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE question ADD COLUMN answerD TEXT")
        }
    }
}
