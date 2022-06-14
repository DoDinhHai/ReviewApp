package com.example.reviewapp.domain.usecase.menu


import com.example.reviewapp.domain.model.Menu
import com.example.reviewapp.domain.repository.MenuRepository

class AddMenu(private val menuRepository: MenuRepository) {
    suspend operator fun invoke(menu: Menu){
        menuRepository.insertMenu(menu)
    }
}