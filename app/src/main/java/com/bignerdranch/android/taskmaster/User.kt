package com.bignerdranch.android.taskmaster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    @ColumnInfo(name = "email") var email:String,
    @ColumnInfo(name = "password") var password:String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "surname") var surname:String
)
