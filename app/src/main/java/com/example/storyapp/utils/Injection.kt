package com.example.storyapp.utils

import android.content.Context
import com.example.storyapp.data.DataRepository
import com.example.storyapp.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val apiService = ApiConfig.getApiService()
        return DataRepository(apiService)
    }
}