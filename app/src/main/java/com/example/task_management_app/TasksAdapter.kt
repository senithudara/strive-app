package com.example.task_management_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksAdapter(private var tasks: List<Task>, private val context: Context) : RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val dueDateTextView: TextView = itemView.findViewById(R.id.dueDateTextView) // Added
        val priorityTextView: TextView = itemView.findViewById(R.id.priorityTextView) // Added
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.titleTextView.text = task.title
        holder.contentTextView.text = task.content
        holder.dueDateTextView.text = "Due Date: ${task.dueDate}" // Bind due date
        holder.priorityTextView.text = "Priority: ${getPriorityText(task.priority)}" // Bind priority
    }

    private fun getPriorityText(priority: Int): String {
        return when (priority) {
            1 -> "Low"
            2 -> "Medium"
            3 -> "High"
            else -> ""
        }
    }

    fun refreshData(viewTasks: List<Task>) {
        tasks = viewTasks
        notifyDataSetChanged()
    }
}
