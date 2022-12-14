package com.apfjuliano.savepass.model

import java.io.Serializable

data class Password(
    val id: Int,
    val name: String,
    val user: String,
    val password: String
): Serializable
