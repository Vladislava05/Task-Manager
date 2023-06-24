package com.bignerdranch.android.taskmaster

import java.io.Serializable

data class Todo(
    var title:String,
    var description:String,
    var date: String,
    var isChecked:Boolean=false
) : Serializable
