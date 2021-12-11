package com.example.icfesg10.model

import java.io.Serializable

data class Cuestionario(
    var id: String,
    var idPregunta: String,
    var idTest: Int,
    var pregunta: String,
    var respuestaCorecta: String,
    var respuesta: String,
    var usuario: String,
) : Serializable
