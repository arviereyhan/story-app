package com.example.storyapp.ui.login

import androidx.lifecycle.ViewModel
import com.example.storyapp.data.DataRepository

class LoginViewModel(private val repository: DataRepository): ViewModel() {
    fun postLogin(email: String, password: String) = repository.login(email, password)
}