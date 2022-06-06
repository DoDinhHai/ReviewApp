package com.example.reviewapp.domain.repository

import com.example.reviewapp.domain.model.Menu
import kotlinx.coroutines.flow.Flow

interface LocalData: Repository {
    suspend fun getMenuList(): List<Menu>

}