package com.example.hwapp.feature.main_feature.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GithubRepoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun githubRepoDao(): GithubRepoDao
}