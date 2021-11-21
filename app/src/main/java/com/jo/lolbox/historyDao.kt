package com.jo.lolbox

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface historyDao {
 @Query("select * from history")
 fun get() : List<history>
 @Insert(onConflict = REPLACE)
 fun insert(history: history)
 @Delete
 fun delete(history: history)
 @Update
 fun upadte(history: history)
}
    