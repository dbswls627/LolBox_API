package com.jo.lolbox

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface historyDao {
 @Query("select * from history")
 fun get() : List<history>
 @Query("select * from history where nation = :nation")
 fun get(nation: String) : List<history>
 @Insert(onConflict = REPLACE)
 fun insert(history: history)
 @Delete
 fun delete(history: history)
 @Update
 fun upadte(history: history)

 @Query("select nation from nation")
 fun getNation() : String
 @Insert(onConflict = REPLACE)
 fun insert(nation: nation)
}
    