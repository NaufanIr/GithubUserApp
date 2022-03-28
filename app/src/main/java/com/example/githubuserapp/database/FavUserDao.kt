package com.example.githubuserapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavUserDao {
    @Query("SELECT * FROM fav_user")
    fun getAllFavUser(): LiveData<List<FavUser>>

    @Query("SELECT count(*) FROM fav_user WHERE fav_user.id = :id")
    fun getFavUserById(id: Int): LiveData<Int>

    @Query("DELETE FROM fav_user WHERE fav_user.id = :id")
    fun removeFromFav(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFav(favUser: FavUser)

}