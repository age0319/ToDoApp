package com.atmc118.todoapp

import android.app.Application
import androidx.room.Room

class RoomSettingApp : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "todos"
        ).build()
    }
}