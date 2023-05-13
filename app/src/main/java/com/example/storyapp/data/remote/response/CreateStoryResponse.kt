package com.example.storyapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class CreateStoryResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)
