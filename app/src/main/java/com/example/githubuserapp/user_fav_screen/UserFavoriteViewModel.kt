package com.example.githubuserapp.user_fav_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuserapp.database.FavUser
import com.example.githubuserapp.database.FavUserRepo

class UserFavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val mFavUserRepo: FavUserRepo = FavUserRepo(application)

    fun getAllFavUser(): LiveData<List<FavUser>> = mFavUserRepo.getAllFavUser()
}