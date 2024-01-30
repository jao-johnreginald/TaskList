package com.johnreg.tasklist.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.johnreg.tasklist.data.Task
import com.johnreg.tasklist.data.TaskDao
import com.johnreg.tasklist.data.TaskListDatabase
import com.johnreg.tasklist.databinding.FragmentTasksBinding
import kotlin.concurrent.thread

class TasksFragment : Fragment(), TasksAdapter.TaskUpdatedListener {

    private lateinit var binding: FragmentTasksBinding

    private val taskDao: TaskDao by lazy {
        val database = TaskListDatabase.getDatabase(requireContext())
        database.getTaskDao()
    }

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

        fetchAllTasks()
    }

    fun fetchAllTasks() {
        thread {
            val tasks = taskDao.getAllTasks()
            requireActivity().runOnUiThread {
                binding.recyclerView.adapter = TasksAdapter(tasks, this)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        thread {
            taskDao.updateTask(task)
        }
    }

}