package com.example.reviewapp.domain.repository

import com.example.reviewapp.data.model.MenuEntity
import com.example.reviewapp.domain.model.Menu
import kotlinx.coroutines.flow.Flow
interface MenuRepository {

    suspend fun getMenus(): List<Menu>

    suspend fun insertMenu(menu: MenuEntity)

    suspend fun deleteMenu(menu: MenuEntity)
}