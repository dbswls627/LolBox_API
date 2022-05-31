package com.jo.lolbox

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import java.util.ArrayList


class MyViewModel(application: Application) : AndroidViewModel(application) {

   // lateinit var historyList : MutableLiveData<ArrayList<history>>

    val db = Room.databaseBuilder(application,
    AppDatabase::class.java, "history.db")
    .fallbackToDestructiveMigration().allowMainThreadQueries()
    .build()

    fun getLiveData() : LiveData<List<history>> {
        return db.historyDao().getLiveData()
    }

    fun getData() : List<history> {
        return db.historyDao().getData()
    }

    fun insert(name:String){
        db.historyDao().insert(history(name))
    }

    fun delete(name: String){
        db.historyDao().delete(history(name))
    }
}