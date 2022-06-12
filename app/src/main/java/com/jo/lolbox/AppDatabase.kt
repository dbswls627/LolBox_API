package com.jo.lolbox

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(history::class), version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): historyDao
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "history.db")
                        .fallbackToDestructiveMigration().allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
    