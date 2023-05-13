package com.example.storyapp.ui.register


import androidx.lifecycle.ViewModel
import com.example.storyapp.data.DataRepository


class RegisterViewModel(private val repository: DataRepository): ViewModel() {
    fun postRegister(name: String, email : String, password: String)= repository.register(name, email, password)

}