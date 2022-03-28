package com.example.githubuserapp.database

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepo(application: Application) {
    private val mFavUserDao: FavUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserRoomDB.getDB(application)
        mFavUserDao = db.favUserDao()
    }

    fun getAllFavUser(): LiveData<List<FavUser>> = mFavUserDao.getAllFavUser()

    fun checkUser(id: Int): LiveData<Int> = mFavUserDao.getFavUserById(id)

    fun addToFav(user: FavUser) {
        executorService.execute { mFavUserDao.addToFav(user) }
    }

    fun removeFromFav(id: Int) {
        executorService.execute { mFavUserDao.removeFromFav(id) }
    }

}