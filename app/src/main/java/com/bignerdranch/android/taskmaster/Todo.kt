package com.bignerdranch.android.taskmaster

import java.io.Serializable
import java.util.*

data class Todo(
    var title:String,
    var description:String,
    var date: Date,
    var isChecked:Boolean=false
) : Serializable
