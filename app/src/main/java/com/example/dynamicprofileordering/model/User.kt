package com.example.dynamicprofileordering.model

data class User(
    val id: Int,
    val name: String,
    val gender: String,
    val photo: String?,
    val about: String?,
    val school: String?,
    val hobbies: List<String>?
)
