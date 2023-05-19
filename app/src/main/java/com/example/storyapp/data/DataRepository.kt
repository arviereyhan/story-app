package com.example.storyapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.data.local.StoryDatabase
import com.example.storyapp.data.remote.response.ListStoryItem
import com.example.storyapp.data.remote.response.LoginResponse
import com.example.storyapp.data.remote.response.RegisterResponse
import com.example.storyapp.data.remote.response.StoryResponse
import com.example.storyapp.data.remote.retrofit.ApiService
import com.example.storyapp.utils.Result
import okhttp3.MultipartBody
import okhttp3.RequestBody

class DataRepository(private val apiService: ApiService, private val database: StoryDatabase) {

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

    fun getStory(token: String): LiveData<PagingData<ListStoryItem>> {
        val bearerToken = generateBearerToken(token)
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(database, apiService, bearerToken),
            pagingSourceFactory = {
//                QuotePagingSource(apiService)
                database.storyDao().getAllStory()
            }
        ).liveData

    }

    fun getStoryWithLocation(loc: Int,token: String): LiveData<Result<StoryResponse>> = liveData{
        try {
            val bearerToken = generateBearerToken(token)
            val response = apiService.getStoriesWithLocation(loc ,bearerToken)
            emit(Result.Success(response))
        }
        catch (e:Exception){
            emit(Result.Error(e.toString()))
        }

    }

    fun createStory(file: MultipartBody.Part, token: String, description: RequestBody, lat: Double?, lon: Double?): LiveData<Result<StoryResponse>> = liveData {
        try {
            val bearerToken = generateBearerToken(token)
            val response = apiService.createStories(file, description, lat, lon,bearerToken)
            emit(Result.Success(response))
        } catch (e:Exception){
            emit(Result.Error(e.toString()))
        }

    }

    private fun generateBearerToken(token: String): String {
        return "Bearer $token"
    }

}