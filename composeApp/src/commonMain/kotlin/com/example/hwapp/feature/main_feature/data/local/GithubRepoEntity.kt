package com.example.hwapp.feature.main_feature.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repos")
data class GithubRepoEntity(
    @PrimaryKey val id: Int,
    val owner: String,
    val name: String,
    val description: String,
    val language: String,
    val stars: Int,
    val forks: Int,
    val avatarUrl: String
)
