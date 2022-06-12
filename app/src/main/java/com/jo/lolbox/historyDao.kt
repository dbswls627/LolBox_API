package com.jo.lolbox

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface historyDao {
 @Query("select * from history order by id desc")
 fun getLiveData(): LiveData<List<history>>

 @Insert(onConflict = REPLACE)
 fun insert(history: history)

 @Query("insert into history(s) values(:name)")
 fun insert(name :String)

 @Delete
 fun delete(history: history)

 @Query("delete from history where s= :name ")
 fun delete(name :String)

 @Update
 fun upadte(history: history)
}
    