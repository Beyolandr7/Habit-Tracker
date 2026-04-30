package com.aenempeh.habittracker.viewmodel

import android.R
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.aenempeh.habittracker.model.Habit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HabitViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("habits_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun createHabit(name: String, desc: String, goal: Int, unit: String, icon: String) {
        try {
            val newHabit = Habit(name, desc, goal, unit, icon)

            val currentList = getHabits().toMutableList()
            currentList.add(newHabit)

            val json = gson.toJson(currentList)
            prefs.edit().putString("habits_list", json).apply()

            Log.d("HabitViewModel", "Habit added successfully: $newHabit")

        } catch (e: Exception) {
            Log.e("HabitViewModel", "Failed to add habit", e)
        }
    }

    fun getHabits(): List<Habit> {
        val json = prefs.getString("habits_list", null) ?: return emptyList()
        val type = object : TypeToken<List<Habit>>() {}.type
        return gson.fromJson(json, type)
    }
}