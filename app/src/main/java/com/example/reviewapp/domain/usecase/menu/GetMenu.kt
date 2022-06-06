package com.example.reviewapp.domain.usecase.menu

import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.repository.MenuRepository

class GetMenu(private val menuRepository: MenuRepository) {
    suspend fun getMenus(): List<Menu>{
        return menuRepository.getMenus()
    }
}