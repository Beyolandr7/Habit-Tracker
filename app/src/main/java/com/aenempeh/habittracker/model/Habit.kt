package com.aenempeh.habittracker.model

data class Habit(
    val name: String,
    val description: String,
    val goal: Int,
    val unit: String,
    val icon: String
)