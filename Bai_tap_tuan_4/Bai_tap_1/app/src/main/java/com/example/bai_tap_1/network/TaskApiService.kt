package com.example.bai_tap_1.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TaskApiService {

    @GET("mock/teach/tasks")
    suspend fun getTasks(
        @Header("Authorization") token: String = "Bearer TuanTruong-uth-student"
    ): Response<List<TaskDto>>

    @GET("mock/teach/tasks/{id}")
    suspend fun getTaskDetail(
        @Path("id") taskId: String,
        @Header("Authorization") token: String = "Bearer TuanTruong-uth-student"
    ): Response<TaskDto>

    @PATCH("mock/teach/tasks/{id}")
    suspend fun updateTask(
        @Path("id") taskId: String,
        @Body body: Map<String, @JvmSuppressWildcards Any>,
        @Header("Authorization") token: String = "Bearer TuanTruong-uth-student"
    ): Response<TaskDto>

    @DELETE("mock/teach/tasks/{id}")
    suspend fun deleteTask(
        @Path("id") taskId: String,
        @Header("Authorization") token: String = "Bearer TuanTruong-uth-student"
    ): Response<Unit>

    companion object {
        private const val BASE_URL = "https://share.tryapps.store/"

        fun create(): TaskApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TaskApiService::class.java)
        }
    }
}
