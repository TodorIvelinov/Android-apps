package com.example.todolist.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.database.Task
import com.example.todolist.database.TaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoScreenViewModel(private val database: TaskDatabaseDao) : ViewModel() {

    val todoList: LiveData<List<Task>> = database.getAllTasks()

    fun onTodoTaskAdded(taskName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            insert(todoTask = Task(text = taskName))
        }
    }

    private fun insert(todoTask: Task) {
        database.insert(todoTask)
    }
}
