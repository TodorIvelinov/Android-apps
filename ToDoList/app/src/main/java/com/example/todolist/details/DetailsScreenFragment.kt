package com.example.todolist.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.todo.TASK_KEY
import com.example.todolist.database.Task
import com.example.todolist.database.TaskDatabase
import com.example.todolist.databinding.FragmentDetailsScreenBinding
import com.example.todolist.serializable

class DetailsScreenFragment : Fragment() {

    private lateinit var detailsScreenViewModel: DetailScreenViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val currentTask: Task? = arguments?.serializable(TASK_KEY)

        val binding: FragmentDetailsScreenBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details_screen, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TaskDatabase.getInstance(application).taskDatabaseDao
        val viewModelFactory = DetailsScreenViewModelFactory(dataSource)

        detailsScreenViewModel =
            ViewModelProvider(this, viewModelFactory)[DetailScreenViewModel::class.java]

        binding.detailScreenViewModel = detailsScreenViewModel
        binding.lifecycleOwner = this
        detailsScreenViewModel.setCurrentTask(currentTask)

        setObservers()

        return binding.root
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_detailsScreenFragment_to_todoScreenFragment)
    }

    private fun setObservers() {
        detailsScreenViewModel.navigationEvent.observe(viewLifecycleOwner) {
            if (it == true) {
                navigateBack()
            }
        }
    }
}
