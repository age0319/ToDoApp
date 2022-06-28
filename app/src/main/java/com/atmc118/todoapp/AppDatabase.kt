package com.atmc118.todoapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): ToDoDao
}