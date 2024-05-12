package com.example.task_management_app

data class Task(val id: Int, val title: String, val content: String, var completed: Boolean = false)
