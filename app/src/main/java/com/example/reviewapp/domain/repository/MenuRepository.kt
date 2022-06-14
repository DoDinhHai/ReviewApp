package com.example.reviewapp.domain.repository

import com.example.reviewapp.domain.model.Menu
import kotlinx.coroutines.flow.Flow

interface MenuRepository {

    fun getMenus(): Flow<List<Menu>>

    suspend fun insertMenu(menu: Menu)

    suspend fun deleteMenu(menu: Menu)
}