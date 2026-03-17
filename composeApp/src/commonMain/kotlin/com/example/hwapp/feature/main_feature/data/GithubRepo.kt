package com.example.hwapp.feature.main_feature.data

import com.example.hwapp.feature.main_feature.data.local.GithubRepoDao
import com.example.hwapp.feature.main_feature.data.local.GithubRepoEntity
import com.example.hwapp.feature.main_feature.data.models.GithubSearchResponse
import com.example.hwapp.feature.main_feature.presentation.GithubRepoItem
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class GithubRepository(
    private val dao: GithubRepoDao?
) {
    suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int = 20
    ): Result<List<GithubRepoItem>> {
        return try {
            val response: GithubSearchResponse =
                NetworkClient.httpClient.get("https://api.github.com/search/repositories") {
                    parameter("q", query)
                    parameter("page", page)
                    parameter("per_page", perPage)
                }.body()

            val items = response.items.map { dto ->
                GithubRepoItem(
                    id = dto.id,
                    owner = dto.owner.login,
                    name = dto.name,
                    description = dto.description ?: "Нет описания",
                    language = dto.language ?: "Unknown",
                    stars = dto.stargazersCount ?: 0,
                    forks = dto.forksCount ?: 0,
                    avatarUrl = dto.owner.avatarUrl ?: ""
                )
            }

            val entities = items.map {
                GithubRepoEntity(
                    it.id,
                    it.owner,
                    it.name,
                    it.description,
                    it.language,
                    it.stars,
                    it.forks,
                    it.avatarUrl
                )
            }
            dao?.let {
                if (page == 1) it.clearAll()
                it.insertRepos(entities)
            }

            Result.success(items)
        } catch (e: Exception) {
            val cached = dao?.getAllRepos()?.map {
                GithubRepoItem(
                    it.id,
                    it.owner,
                    it.name,
                    it.description,
                    it.language,
                    it.stars,
                    it.forks,
                    it.avatarUrl
                )
            } ?: emptyList()

            if (cached.isNotEmpty()) {
                Result.success(cached)
            } else {
                Result.failure(e)
            }
        }
    }
}