package com.example.storyapp.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var token: String? = null
):Parcelable
