package com.johnreg.tasklist.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.johnreg.tasklist.data.Task
import com.johnreg.tasklist.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = TasksAdapter(
            tasks = listOf(
                Task(title = "First task", description = "Barney is a dinosaur from our imagination"),
                Task(title = "Second task", description = "And when he's tall"),
                Task(title = "Third task", description = "He's what we call a dinosaur sensation")
            )
        )
    }

}