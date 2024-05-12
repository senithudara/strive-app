package com.example.task_management_app

data class Task(
    val id: Int,
    val title: String,
    val content: String,
    val dueDate: String,
    val priority: Int
)
