package com.example.storyapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.storyapp.data.remote.response.ListStoryItem
import com.example.storyapp.data.remote.response.LoginResponse
import com.example.storyapp.data.remote.response.RegisterResponse
import com.example.storyapp.data.remote.response.StoryResponse
import com.example.storyapp.data.remote.retrofit.ApiService
import com.example.storyapp.utils.Preferences
import com.example.storyapp.utils.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.math.log

class DataRepository(private val apiService: ApiService) {

    fun register(name: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData{
        try {
            val response = apiService.register(name, email, password)
            emit(Result.Success(response))
        }
        catch(e: Exception){
            emit(Result.Error(e.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData{
        try {
            val response = apiService.login(email, password)
            emit(Result.Success(response))
        }
        catch(e: Exception){
            emit(Result.Error(e.toString()))
        }
    }

    fun getStory(token: String): LiveData<Result<StoryResponse>> = liveData{
        try {
            val bearerToken = generateBearerToken(token)
            val response = apiService.getStories(page = 1, size = 100, bearerToken)
            emit(Result.Success(response))
        }
        catch (e:Exception){
            emit(Result.Error(e.toString()))
        }

    }

    fun createStory(file: MultipartBody.Part, token: String, description: RequestBody): LiveData<Result<StoryResponse>> = liveData {
        try {
            val bearerToken = generateBearerToken(token)
            val response = apiService.createStories(file, description, bearerToken)
            emit(Result.Success(response))
        } catch (e:Exception){
            emit(Result.Error(e.toString()))
        }

    }

    private fun generateBearerToken(token: String): String {
        return "Bearer $token"
    }

}