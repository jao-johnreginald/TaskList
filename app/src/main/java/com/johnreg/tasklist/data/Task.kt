package com.johnreg.tasklist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
    val title: String,
    val description: String? = null,
    val isStarred: Boolean = false
)
