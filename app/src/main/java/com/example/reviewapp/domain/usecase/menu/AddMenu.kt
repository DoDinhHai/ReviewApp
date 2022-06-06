package com.example.reviewapp.domain.usecase.menu

import com.example.reviewapp.data.model.MenuEntity
import com.example.reviewapp.domain.repository.MenuRepository

class AddMenu(private val menuRepository: MenuRepository) {
    suspend fun invoke(menuEntity: MenuEntity){
        menuRepository.insertMenu(menuEntity)
    }
}