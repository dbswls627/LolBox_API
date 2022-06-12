package com.jo.lolbox

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
    data class history(
        @PrimaryKey(autoGenerate = true)
        var id:Int,
        var s:String
)
    