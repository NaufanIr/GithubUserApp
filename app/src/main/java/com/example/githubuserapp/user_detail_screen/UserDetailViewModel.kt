package com.example.githubuserapp.user_detail_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.data.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {

    val userData = MutableLiveData<UserDetailResponse>()

    fun setData(username : String) {
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetailResponse>{
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
               if(response.isSuccessful) {
                   val responseBody = response.body()
                   if(responseBody != null) {
                       userData.postValue(responseBody)
                   }
               }
            }
            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.d("Failur : ", t.message.toString())
            }
        })
    }

    fun getData() : LiveData<UserDetailResponse>{
        return userData
    }
}