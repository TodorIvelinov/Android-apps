package com.example.todolist.todo

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.database.Task
import com.example.todolist.database.TaskDatabase
import com.example.todolist.adapter.IOnItemClick
import com.example.todolist.adapter.TodoAdapter
import com.example.todolist.databinding.FragmentTodoScreenBinding

const val TASK_KEY = "task_key"

class TodoScreenFragment : Fragment(), IOnItemClick {

    private lateinit var adapter: TodoAdapter
    private lateinit var todoScreenViewModel: TodoScreenViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding: FragmentTodoScreenBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_todo_screen,
            container,
            false
        )
        val dataSource = TaskDatabase.getInstance(requireActivity().application).taskDatabaseDao
        val viewModelFactory = TodoScreenViewModelFactory(dataSource)
        todoScreenViewModel =
            ViewModelProvider(
                this@TodoScreenFragment,
                viewModelFactory
            )[TodoScreenViewModel::class.java]
        setObservers()
        adapter = TodoAdapter(emptyList(), this@TodoScreenFragment)
        with(binding) {
            lifecycleOwner = this@TodoScreenFragment
            fragmentTodoRv.adapter = adapter
            setClickListeners(this)
            return root
        }
    }

    private fun setClickListeners(binding: FragmentTodoScreenBinding) {
        binding.fragmentTodoToolbarButton.setOnClickListener {
            showNewTaskDialog(requireContext())
        }
    }

    private fun setObservers() {
        todoScreenViewModel.todoList.observe(viewLifecycleOwner) { task ->
            adapter.updateData(task)
        }
    }

    private fun showNewTaskDialog(context: Context) {
        AlertDialog.Builder(context).apply {
            setTitle(getString(R.string.dialog_set_task_title_text))

            val editText = EditText(context)
            setView(editText)

            setPositiveButton(getString(R.string.dialog_positive_button_text)) { _, _ ->
                todoScreenViewModel.onTodoTaskAdded(taskName = editText.text.toString())
            }

            setNegativeButton(getString(R.string.dialog_negative_button_text)) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun onItemClick(item: Task) {
        navigateToDetailsFragment(item)
    }

    private fun navigateToDetailsFragment(item: Task) {
        findNavController().navigate(
            R.id.action_todoScreenFragment_to_detailsScreenFragment,
            bundleOf(TASK_KEY to item)
        )
    }
}
