package com.example.storyapp.utils

import android.content.Context
import com.example.storyapp.data.DataRepository
import com.example.storyapp.data.local.StoryDatabase
import com.example.storyapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return DataRepository(apiService, database)
    }
}