package com.example.task_management_app

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Paint
import android.widget.CheckBox

class TasksAdapter(private var tasks: List<Task>, private val context: Context) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private val db: TaskDatabaseHelper = TaskDatabaseHelper(context)

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val completedCheckbox: CheckBox = itemView.findViewById(R.id.completedCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        // Set task title and content
        holder.titleTextView.text = task.title
        holder.contentTextView.text = task.content

        // Check if the task is completed
        holder.completedCheckbox.isChecked = task.completed
        if (task.completed) {
            // Apply strikethrough if task is completed
            holder.titleTextView.paintFlags = holder.titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.contentTextView.paintFlags = holder.contentTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            // Remove strikethrough if task is not completed
            holder.titleTextView.paintFlags = holder.titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.contentTextView.paintFlags = holder.contentTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        // Set listener for checkbox state change
        holder.completedCheckbox.setOnCheckedChangeListener { _, isChecked ->
            // Update task completion status
            task.completed = isChecked
            // Update UI to reflect completion status
            if (isChecked) {
                holder.titleTextView.paintFlags = holder.titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.contentTextView.paintFlags = holder.contentTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.titleTextView.paintFlags = holder.titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                holder.contentTextView.paintFlags = holder.contentTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateTaskActivity::class.java).apply {
                putExtra("task_id", task.id)
            }

            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            db.deleteTask(task.id)
            refreshData(db.getAllTasks())
            Toast.makeText(holder.itemView.context, "Task Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(viewTasks: List<Task>) {
        tasks = viewTasks
        notifyDataSetChanged()
    }
}
