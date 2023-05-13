package com.example.storyapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.storyapp.data.DataRepository

class MainViewModel(private val repository: DataRepository): ViewModel() {
    fun getStory(token: String) = repository.getStory(token)
}