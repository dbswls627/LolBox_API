package com.jo.lolbox

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room


class MyViewModel(application: Application) : AndroidViewModel(application) {

    // lateinit var historyList : MutableLiveData<ArrayList<history>>

    val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "history.db"
    )
        .fallbackToDestructiveMigration().allowMainThreadQueries()
        .build()

    val list = getLiveData()
    fun getLiveData(): LiveData<List<history>> {
        return db.historyDao().getLiveData()
    }

    fun insert(name: String, profileIconId: String) {
        db.historyDao().insert(profileIconId,name )
    }

    fun delete(name: String) {
        db.historyDao().delete(name)
    }


}
