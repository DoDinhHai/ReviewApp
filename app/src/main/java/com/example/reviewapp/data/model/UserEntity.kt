package com.example.reviewapp.data.model

import androidx.room.Entity
import com.example.reviewapp.data.base.EntityMapper
import com.example.reviewapp.data.base.ModelEntity
import com.example.reviewapp.domain.model.User
import com.google.gson.annotations.SerializedName
import javax.inject.Inject

@Entity(tableName = "user", primaryKeys = ["id"])
data class UserEntity(
    @field: SerializedName("id") val id: String,
    @field: SerializedName("name") val name: String,
    @field: SerializedName("username") val username: String,
    @field: SerializedName("passWord") val passWord: String,
    @field: SerializedName("email") val email: String,
    @field: SerializedName("phone") val phone: String,
    @field: SerializedName("address") val address: String
) : ModelEntity()

class UserEntityMapper @Inject constructor() : EntityMapper<User, UserEntity> {
    override fun mapToDomain(entity: UserEntity): User = User(
        id = entity.id,
        name = entity.name,
        username = entity.username,
        passWord = entity.passWord,
        email = entity.email,
        phone = entity.phone,
        address = entity.address
    )

    override fun mapToEntity(model: User): UserEntity = UserEntity(
        id = model.id,
        name = model.name,
        username = model.username,
        passWord = model.passWord,
        email = model.email,
        phone = model.phone,
        address = model.address
    )
}
