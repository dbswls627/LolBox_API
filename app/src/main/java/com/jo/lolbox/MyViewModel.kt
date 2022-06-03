package com.jo.lolbox

import android.app.Application
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import java.util.ArrayList


class MyViewModel(application: Application) : AndroidViewModel(application) {

   // lateinit var historyList : MutableLiveData<ArrayList<history>>

    val db = Room.databaseBuilder(application,
    AppDatabase::class.java, "history.db")
    .fallbackToDestructiveMigration().allowMainThreadQueries()
    .build()

    val list = getLiveData()
    fun getLiveData() : LiveData<List<history>> {
        return db.historyDao().getLiveData()
    }

    fun insert(name:String){
        db.historyDao().insert(history(name))
    }

    fun delete(name: String){
        db.historyDao().delete(history(name))
    }



}
