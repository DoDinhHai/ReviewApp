package com.example.reviewapp.data.model

import androidx.room.Entity
import com.example.reviewapp.data.base.EntityMapper
import com.example.reviewapp.data.base.ModelEntity
import com.example.reviewapp.domain.model.Menu
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

@Entity(tableName = "menu", primaryKeys = ["id"])
data class MenuEntity(
    @field: SerializedName("id") val id: Int,
    @field: SerializedName("name") val name: String,
    @field: SerializedName("img") val img : Int
) : ModelEntity()

class MenuEntityMapper @Inject constructor() : EntityMapper<Menu, MenuEntity> {
    override fun mapToDomain(entity: MenuEntity): Menu = Menu(
        id = entity.id,
        name = entity.name,
        img = entity.img,
    )

    override fun mapToEntity(model: Menu): MenuEntity = MenuEntity(
        id = model.id,
        name = model.name,
        img = model.img,
    )
}
