package com.example.githubuserapp.api

import com.example.githubuserapp.data.UserDetailResponse
import com.example.githubuserapp.data.UserSearchResponse
import com.example.githubuserapp.data.UsersData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUserSearch(
        @Query("q") query: String
    ): Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<UsersData>>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<UsersData>>
}