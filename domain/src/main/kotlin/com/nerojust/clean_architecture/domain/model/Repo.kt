package com.nerojust.clean_architecture.domain.model

data class Repo(
    val id: Long,
    val fullName: String,
    val description: String?,
    val htmlUrl: String,
    val stars: Int,
    val language: String?,
    val ownerLogin: String,
    val ownerAvatarUrl: String,
)
