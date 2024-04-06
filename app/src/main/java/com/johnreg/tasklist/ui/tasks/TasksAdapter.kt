package com.johnreg.tasklist.ui.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.johnreg.tasklist.data.Task
import com.johnreg.tasklist.databinding.ItemTaskBinding

class TasksAdapter(private val tasks: List<Task>, private val listener: TaskUpdatedListener) : RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.checkBox.isChecked = task.isComplete
            binding.toggleStar.isChecked = task.isStarred
            if (task.isComplete) {
                binding.tvTitle.paintFlags = binding.tvTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.tvDetails.paintFlags = binding.tvDetails.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            binding.tvTitle.text = task.title
            binding.tvDetails.text = task.description

            // Listen for checkbox changes and communicate back to the Fragment
            binding.checkBox.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> task.copy(isComplete = true)
                    else -> task.copy(isComplete = false)
                }
                listener.onTaskUpdated(updatedTask)
            }
            binding.toggleStar.addOnCheckedStateChangedListener { _, state ->
                val updatedTask = when (state) {
                    MaterialCheckBox.STATE_CHECKED -> task.copy(isStarred = true)
                    else -> task.copy(isStarred = false)
                }
                listener.onTaskUpdated(updatedTask)
            }
        }

    }

    interface TaskUpdatedListener {

        fun onTaskUpdated(task: Task)

    }

}