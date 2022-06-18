package com.syed.myapplication.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseResponseModel(
    @Expose @SerializedName("userId") val userId: Int?,
    @Expose @SerializedName("id") var id: Int?,
    @Expose @SerializedName("title") val title: String,
    @Expose @SerializedName("body") val body: String? = null
) : Serializable
