package com.bignerdranch.android.taskmaster

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) var id:Long = 0,
    @ColumnInfo(name = "title") var title:String,
    @ColumnInfo(name = "description") var description:String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "isChecked") var isChecked:Boolean=false
) : Serializable
