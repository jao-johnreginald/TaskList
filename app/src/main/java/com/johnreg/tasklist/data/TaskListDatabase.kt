package com.johnreg.tasklist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class TaskListDatabase : RoomDatabase() {

    abstract fun getTaskDao() : TaskDao

    companion object {

        fun createDatabase(context: Context): TaskListDatabase {
            return Room.databaseBuilder(
                context,
                TaskListDatabase::class.java,
                "task-list-database"
            ).build()
        }

    }

}