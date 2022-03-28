package com.example.githubuserapp.home_screen

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuserapp.SettingPreferences
import com.example.githubuserapp.api.ApiConfig
import com.example.githubuserapp.data.UserSearchResponse
import com.example.githubuserapp.data.UsersData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    private val listUser = MutableLiveData<List<UsersData>>()
    var isInitUser = true

    fun setIsInitUser(e: Boolean) {
        isInitUser = e
    }

    fun searchUsers(query: String) {
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

    fun getSearchUsers() : LiveData<List<UsersData>> = listUser

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemSetting().asLiveData()
    }

}