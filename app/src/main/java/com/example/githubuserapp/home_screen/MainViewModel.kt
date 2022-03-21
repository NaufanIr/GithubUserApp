package com.example.githubuserapp.home_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.data.UserSearchResponse
import com.example.githubuserapp.data.UsersData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<List<UsersData>>()

    fun searchUsers(query: String){
        val client = ApiConfig.getApiService().getUserSearch(query)
        client.enqueue(object : Callback<UserSearchResponse>{

            override fun onResponse(
                call: Call<UserSearchResponse>,
                response: Response<UserSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null){
                        listUser.postValue(responseBody.items)
                    }
                }
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                Log.d("Failur : ", t.message.toString())
            }
        })
    }

    fun getSearchUsers() : LiveData<List<UsersData>>{
        return listUser
    }
}