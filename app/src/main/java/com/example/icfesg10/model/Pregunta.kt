package com.example.icfesg10.model
import java.io.Serializable

data class Pregunta(
    var id: String,
    var PreTexto: String,
    var Opcion1: String,
    var Opcion2: String,
    var Opcion3: String,
    var Respuesta: String,
    var Area: String,
    var Descripcion: String
):Serializable
