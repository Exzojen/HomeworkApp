package com.example.hwapp.feature.main_feature.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubSearchResponse(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("items") val items: List<GithubRepoDto>
)

@Serializable
data class GithubRepoDto(
    val id: Int,
    val name: String,
    val description: String? = null,
    val language: String? = null,
    @SerialName("stargazers_count") val stargazersCount: Int? = 0,
    @SerialName("forks_count") val forksCount: Int? = 0,
    val owner: OwnerDto
)

@Serializable
data class OwnerDto(
    val login: String,
    @SerialName("avatar_url") val avatarUrl: String? = null
)
