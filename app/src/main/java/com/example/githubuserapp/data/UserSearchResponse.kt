package com.example.githubuserapp.data

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("items")
	val items: List<UsersData>
)

data class UsersData(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val login: String
)
