package com.johnreg.tasklist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johnreg.tasklist.data.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskListDatabase : RoomDatabase() {

    abstract fun getTaskDao() : TaskDao

}