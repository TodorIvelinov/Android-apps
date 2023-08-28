package com.example.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tasks_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Int = 0,
    @ColumnInfo(name = "task_text_info")
    var text: String = "",
    @ColumnInfo(name = "task_status_info")
    var isCompleted: Boolean = false
) : Serializable
