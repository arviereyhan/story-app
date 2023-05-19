package com.example.storyapp.ui.create

import androidx.lifecycle.ViewModel
import com.example.storyapp.data.DataRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateStoryViewModel(private val repository: DataRepository): ViewModel() {
    fun createStory(file: MultipartBody.Part, token: String, description: RequestBody, lat: Double?, lon: Double?) = repository.createStory(file, token, description, lat, lon)

}