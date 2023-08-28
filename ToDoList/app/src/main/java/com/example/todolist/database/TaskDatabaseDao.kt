package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDatabaseDao {

    @Insert
    fun insert(task: Task)

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM tasks_table ORDER BY taskId DESC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks_table ORDER BY taskId DESC LIMIT 1")
    fun getCurrentTask(): Task?
}
