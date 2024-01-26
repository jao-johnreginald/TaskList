package com.johnreg.tasklist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.johnreg.tasklist.data.Task
import com.johnreg.tasklist.data.TaskListDatabase
import com.johnreg.tasklist.databinding.ActivityMainBinding
import com.johnreg.tasklist.databinding.DialogAddTaskBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = PagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.pager) { tab, _ ->
            tab.text = "Tasks"
        }.attach()

        binding.fab.setOnClickListener { showAddTaskDialog() }

        val database = TaskListDatabase.createDatabase(this)

        val taskDao = database.getTaskDao()

        thread {
            taskDao.createTask(Task(
                title = "hello task",
                description = "description hello world",
                isStarred = true
            ))
            val tasks = taskDao.getAllTasks()
            runOnUiThread {
                Toast.makeText(this, "Number of tasks: ${tasks.size}", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun showAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(this)
            .setTitle("Add new task")
            .setView(dialogBinding.root)
            .setPositiveButton("Save") { _, _ ->
                Toast.makeText(this, "Your task is: ${dialogBinding.editText.text}", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount() = 1

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }
    }

}