package com.example.reviewapp.data.repository

import com.example.reviewapp.data.local.db.dao.MenuDao
import com.example.reviewapp.data.model.MenuEntity
import com.example.reviewapp.data.model.MenuEntityMapper
import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(private val dao: MenuDao, private val menuEntityMapper: MenuEntityMapper) : MenuRepository {

     override fun getMenus(): Flow<List<Menu>> {
        return dao.getAll()
    }

    override suspend fun insertMenu(menu: Menu) {
        dao.insert(menuEntityMapper.mapToEntity(menu))
    }

    override suspend fun deleteMenu(menu: Menu) {
        dao.delete(menuEntityMapper.mapToEntity(menu))
    }
}