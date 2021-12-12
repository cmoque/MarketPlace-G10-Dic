package com.example.icfesg10.model

import java.io.Serializable

data class User(
    val uid: String,
    val name: String,
    val lastname: String,
    val username: String,
    val email: String,
    val role: Int
): Serializable

