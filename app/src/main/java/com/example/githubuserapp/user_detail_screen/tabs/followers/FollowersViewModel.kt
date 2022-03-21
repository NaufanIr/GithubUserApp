package com.example.githubuserapp.user_detail_screen.tabs.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.data.UsersData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listData = MutableLiveData<List<UsersData>>()

    fun setData(username: String){
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<UsersData>>{
            override fun onResponse(call: Call<List<UsersData>>, response: Response<List<UsersData>>) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null) {
                        listData.postValue(responseBody)
                    }
                }
            }
            override fun onFailure(call: Call<List<UsersData>>, t: Throwable) {
                Log.d("Failur : ", t.message.toString())
            }
        })
    }

    fun getData() : LiveData<List<UsersData>>{
        return listData
    }
}