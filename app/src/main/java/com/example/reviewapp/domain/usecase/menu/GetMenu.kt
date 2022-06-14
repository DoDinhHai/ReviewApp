package com.example.reviewapp.domain.usecase.menu

import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow

class GetMenu(private val menuRepository: MenuRepository) {
    operator fun invoke(): Flow<List<Menu>> {
        return menuRepository.getMenus()
    }
}