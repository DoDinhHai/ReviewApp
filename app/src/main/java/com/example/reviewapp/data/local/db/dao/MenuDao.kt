package com.example.reviewapp.data.local.db.dao

import androidx.room.*
import com.example.reviewapp.data.model.MenuEntity
import com.example.reviewapp.domain.model.Menu
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(menu: MenuEntity): Unit

    @Query("SELECT * FROM menu")
    fun getAll(): Flow<List<Menu>>

    @Delete
    fun delete(menu: MenuEntity)
}