package com.example.storyapp.ui.maps

import androidx.lifecycle.ViewModel
import com.example.storyapp.data.DataRepository

class MapsViewModel(private val repository: DataRepository): ViewModel() {
    fun getStoryWithLocation(loc: Int, token: String) = repository.getStoryWithLocation(loc, token)
}