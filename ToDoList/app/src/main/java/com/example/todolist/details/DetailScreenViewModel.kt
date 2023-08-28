package com.example.todolist.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.database.Task
import com.example.todolist.database.TaskDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailScreenViewModel(private val database: TaskDatabaseDao) : ViewModel() {

    private val _navigationEvent = MutableLiveData(false)
    val navigationEvent: LiveData<Boolean>
        get() = _navigationEvent

    private val _currentTask = MutableLiveData<Task>()
    private val currentTask: LiveData<Task>
        get() = _currentTask

    val currentTaskText = MutableLiveData<String>()

    val isCompleteButtonEnabled = Transformations.map(currentTaskText) {
        currentTask.value?.isCompleted == true && currentTask.value?.text == it
    }

    fun onDeleteButtonClick() {
        viewModelScope.launch {
            currentTask.value?.let { delete(it) }
            navigateBack()
        }
    }

    private suspend fun delete(todoTask: Task) = withContext(Dispatchers.IO) {
        database.delete(todoTask)
    }

    fun onSaveButtonClick() {
        updateCurrentTask()
        navigateBack()
    }

    fun onCompleteButtonClick() {
        updateCurrentTask(true)
        navigateBack()
    }

    private fun navigateBack() {
        _navigationEvent.value = true
    }

    private fun updateCurrentTask(isCompleted: Boolean = false) {
        currentTask.value?.let {
            update(
                todoTask = it.copy(
                    text = currentTaskText.value.orEmpty(),
                    isCompleted = if (isCompleted) true else it.isCompleted
                )
            )
        }
    }

    private fun update(todoTask: Task) = viewModelScope.launch(Dispatchers.IO) {
        database.update(todoTask)
    }

    fun setCurrentTask(currentTask: Task?) {
        currentTask?.let {
            _currentTask.value = it
            currentTaskText.value = it.text
        }
    }
}
