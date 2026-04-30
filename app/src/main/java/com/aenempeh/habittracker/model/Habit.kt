package com.aenempeh.habittracker.model

data class Habit(
    val id: String,
    val name: String,
    val description: String,
    val goal: Int,
    val unit: String,
    val icon: String,
    var currentCount: Int = 0
) {
    fun isCompleted() = currentCount >= goal
    fun progressPercent() = if (goal > 0)
        (currentCount.toFloat() / goal * 100).toInt().coerceAtMost(100)
    else 0
}