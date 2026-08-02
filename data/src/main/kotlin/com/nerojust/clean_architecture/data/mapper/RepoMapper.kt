package com.nerojust.clean_architecture.data.mapper

import com.nerojust.clean_architecture.core.network.dto.RepoDto
import com.nerojust.clean_architecture.domain.model.Repo

fun RepoDto.toDomain(): Repo =
    Repo(
        id = id,
        fullName = fullName,
        description = description,
        htmlUrl = htmlUrl,
        stars = stargazersCount,
        language = language,
        ownerLogin = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
    )
