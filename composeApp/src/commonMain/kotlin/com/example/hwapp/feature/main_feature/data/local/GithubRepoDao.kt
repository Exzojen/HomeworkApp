package com.example.hwapp.feature.main_feature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GithubRepoDao {
    @Query("SELECT * FROM github_repos")
    suspend fun getAllRepos(): List<GithubRepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<GithubRepoEntity>)

    @Query("DELETE FROM github_repos")
    suspend fun clearAll()
}