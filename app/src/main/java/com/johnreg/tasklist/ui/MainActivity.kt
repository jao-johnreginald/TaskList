package com.johnreg.tasklist.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.johnreg.tasklist.data.Task
import com.johnreg.tasklist.data.TaskDao
import com.johnreg.tasklist.data.TaskListDatabase
import com.johnreg.tasklist.databinding.ActivityMainBinding
import com.johnreg.tasklist.databinding.DialogAddTaskBinding
import com.johnreg.tasklist.ui.tasks.TasksFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var database: TaskListDatabase

    private val taskDao: TaskDao by lazy { database.getTaskDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pager.adapter = PagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.pager) { tab, _ ->
            tab.text = "Tasks"
        }.attach()

        binding.fab.setOnClickListener { showAddTaskDialog() }

        database = TaskListDatabase.getDatabase(this)
    }

    private fun showAddTaskDialog() {
        val dialogBinding = DialogAddTaskBinding.inflate(layoutInflater)

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.buttonShowDetails.setOnClickListener {
            dialogBinding.etTaskDetails.visibility = when (dialogBinding.etTaskDetails.visibility) {
                View.VISIBLE -> View.GONE
                else -> View.VISIBLE
            }
        }

        dialogBinding.buttonSave.setOnClickListener {
            val task = Task(
                title = dialogBinding.etTaskTitle.text.toString(),
                description = dialogBinding.etTaskDetails.text.toString()
            )
            thread { taskDao.createTask(task) }
            dialog.dismiss()
        }

        dialog.show()
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount() = 1

        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }
    }

}