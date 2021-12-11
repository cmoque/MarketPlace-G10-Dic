package com.example.icfesg10.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "evaluacion")
data class Evaluacion(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val Resultado: Int,
    val UserId: Int,
    val Fecha: String,
    val Hora: String
)
