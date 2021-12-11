package com.example.icfesg10.model
import java.io.Serializable

data class test(
    val id: String,
    val idtest: Int,
    val idpregunta: String,
    val pregunta: String,
    val respuesta: String,
    val resCorrecta: String,
    val usuario: String
):Serializable
