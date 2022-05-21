package com.jo.lolbox

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class nation(
    @PrimaryKey
    var name: String,
    var nation: String
)
    