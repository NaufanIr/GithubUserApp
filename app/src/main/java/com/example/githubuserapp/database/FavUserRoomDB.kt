package com.example.githubuserapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavUser::class], version = 5)
abstract class FavUserRoomDB: RoomDatabase() {
    abstract fun favUserDao(): FavUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavUserRoomDB? = null

        @JvmStatic
        fun getDB(context: Context): FavUserRoomDB {
            if (INSTANCE == null) {
                synchronized(FavUserRoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavUserRoomDB::class.java,
                        "fav_user_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavUserRoomDB
        }
    }
}