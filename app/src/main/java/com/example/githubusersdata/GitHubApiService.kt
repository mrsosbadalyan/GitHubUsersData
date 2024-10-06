package com.example.githubusersdata

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApiService {
    @GET("users")
    fun getUsers(): Call<List<GitHubUser>>

    @GET("users/{username}")
    fun getUserDetails(@Path("username") username: String): Call<GitHubUser>
}
