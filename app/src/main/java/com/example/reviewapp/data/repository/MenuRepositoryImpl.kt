package com.example.reviewapp.data.repository

import com.example.reviewapp.data.local.db.dao.MenuDao
import com.example.reviewapp.data.model.MenuEntity
import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.repository.MenuRepository
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(private val dao: MenuDao) : MenuRepository {

    override suspend fun getMenus(): List<Menu> {
        return dao.getAll()
    }

    override suspend fun insertMenu(menu: MenuEntity) {
        dao.insert(menu)
    }

    override suspend fun deleteMenu(menu: MenuEntity) {
        dao.delete(menu)
    }
}