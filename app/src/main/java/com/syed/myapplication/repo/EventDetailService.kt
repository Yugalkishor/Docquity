package com.syed.myapplication.repo

import com.syed.myapplication.data.BaseResponseModel
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventDetailService {
    @GET("posts")
    suspend fun getEventList() : ArrayList<BaseResponseModel>
 @GET("posts/{id}")
    suspend fun searchEVEnt(@Path("id") id: Int) : BaseResponseModel
}