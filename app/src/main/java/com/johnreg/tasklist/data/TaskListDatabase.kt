package com.johnreg.tasklist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 2)
abstract class TaskListDatabase : RoomDatabase() {

    abstract fun getTaskDao() : TaskDao

    companion object {

        // No matter what thread is running this code, don't store this variable in any thread cache
        // I want you to store this centrally in main memory
        @Volatile
        private var DATABASE_INSTANCE: TaskListDatabase? = null

        fun getDatabase(context: Context): TaskListDatabase {

            /*
            This is how you create a Singleton
            if (we don't have a database) { make one } else { return the existing one }
            Elvis operator - if it's not null, return left, otherwise, return right
            synchronized - do not allow this block of code to run on multiple threads

            If you know you may be changing your database schema,
            you can call .fallbackToDestructiveMigration() and whenever you change the database,
            you increase the database version and this function will delete all data
             */

            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    TaskListDatabase::class.java,
                    "task-list-database"
                ).fallbackToDestructiveMigration().build()
                DATABASE_INSTANCE = instance
                instance
            }

        }

    }

}