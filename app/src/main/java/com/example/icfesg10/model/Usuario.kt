package com.example.icfesg10.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombre: String,
    val apellido: String,
    val usuario: String,
    val pass: String,
    val correo: String,
    val rol: Int
)



