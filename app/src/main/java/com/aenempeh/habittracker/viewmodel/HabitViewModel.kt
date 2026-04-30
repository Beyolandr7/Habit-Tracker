package com.aenempeh.habittracker.viewmodel

import androidx.lifecycle.MutableLiveData
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

    val habitsLD = MutableLiveData<List<Habit>>()

    val isSuccessLD = MutableLiveData<Boolean>()

    fun createHabit(name: String, desc: String, goal: Int, unit: String, icon: String) {
        try {
            val newHabit = Habit(
                id = System.currentTimeMillis().toString(),
                name = name,
                description = desc,
                goal = goal,
                unit = unit,
                icon = icon)

            val currentList = getHabits().toMutableList()
            currentList.add(newHabit)

            val json = gson.toJson(currentList)
            prefs.edit().putString("habits_list", json).apply()
            habitsLD.value = ArrayList(currentList)

            Log.d("HabitViewModel", "Habit added successfully: $newHabit")

            isSuccessLD.value = true

        } catch (e: Exception) {
            Log.e("HabitViewModel", "Failed to add habit", e)
        }
    }

    fun getHabits(): List<Habit> {
        val json = prefs.getString("habits_list", null) ?: return emptyList()
        val type = object : TypeToken<List<Habit>>() {}.type
        return gson.fromJson(json, type)
    }

    fun loadHabits(){
        habitsLD.value = ArrayList(getHabits())
    }

    fun incrementProgress(habitId: String){
        val list = getHabits().toMutableList()
        val h = list.find { it.id == habitId} ?: return
        if (h.currentCount < h.goal){
            h.currentCount++
            prefs.edit().putString("habits_list", gson.toJson(list)).apply()
            habitsLD.value = ArrayList(list)
        }
    }

    fun decrementProgress(habitId: String) {
        val list = getHabits().toMutableList()
        val h = list.find { it.id == habitId } ?: return
        if (h.currentCount > 0) {
            h.currentCount--
            prefs.edit().putString("habits_list", gson.toJson(list)).apply()
            habitsLD.value = ArrayList(list)
        }
    }
}