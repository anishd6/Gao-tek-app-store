package com.example.todolist

import android.icu.text.CaseMap

data class Todo(
    val title: String,
    var isChecked: Boolean = false //false value is a default value
) //very simple class without any logics but only constructors