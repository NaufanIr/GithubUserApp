package com.example.githubuserapp.user_detail_screen

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.data.UserDetailResponse
import com.example.githubuserapp.database.FavUser
import com.example.githubuserapp.database.FavUserRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application): AndroidViewModel(application) {

    private val mFavUserRepo: FavUserRepo = FavUserRepo(application)
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

    fun getData(): LiveData<UserDetailResponse> = userData

    fun checkUser(id: Int): LiveData<Int> = mFavUserRepo.checkUser(id)

    fun addToFav(user: FavUser) {
        mFavUserRepo.addToFav(user)
    }

    fun removeFromFav(id: Int) {
        mFavUserRepo.removeFromFav(id)
    }

}
