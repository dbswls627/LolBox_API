package com.jo.lolbox

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface historyDao {
 @Query("select * from history")
 fun getLiveData(): LiveData<List<history>>

 @Query("select * from history")
 fun getData(): List<history>

 @Insert(onConflict = REPLACE)
 fun insert(history: history)

 @Delete
 fun delete(history: history)

 @Update
 fun upadte(history: history)
}
    