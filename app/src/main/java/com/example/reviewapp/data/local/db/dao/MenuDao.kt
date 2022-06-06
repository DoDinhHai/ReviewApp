package com.example.reviewapp.data.local.db.dao

import androidx.room.*
import com.example.reviewapp.data.model.MenuEntity
import com.example.reviewapp.domain.model.Menu
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(menu: MenuEntity)

    @Query("SELECT * FROM menu")
    suspend fun getAll(): List<Menu>

    @Delete
    fun delete(menu: MenuEntity)
}