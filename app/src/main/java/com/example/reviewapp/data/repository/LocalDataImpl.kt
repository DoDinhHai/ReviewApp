package com.example.reviewapp.data.repository

import com.example.reviewapp.ReviewApplication
import com.example.reviewapp.data.local.db.AppDatabase
import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.repository.LocalData
import kotlinx.coroutines.flow.Flow

class LocalDataImpl private constructor(
    private val app : ReviewApplication
): LocalData {

    private val appDb = AppDatabase.getInstance(app)
    override suspend fun getMenuList(): List<Menu> {
        return appDb.menuDao().getAll()
    }
}