package com.jo.lolbox

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys= ["s","nation"])
    data class history(
        var s:String,
        var nation:String
)
    