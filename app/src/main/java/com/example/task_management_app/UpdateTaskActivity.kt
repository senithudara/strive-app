package com.example.task_management_app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.task_management_app.databinding.ActivityUpdateTaskBinding
import com.example.task_management_app.Task
import com.example.task_management_app.TaskDatabaseHelper
import android.widget.Toast

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTaskBinding
    private lateinit var db: TaskDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        val taskId = intent.getIntExtra("task_id", -1)
        if (taskId == -1) {
            finish()
            return
        }

        val task = db.getTaskByID(taskId)
        binding.updateTitleEditText.setText(task.title)
        binding.updateContentEditText.setText(task.content)

        setupTextWatcher(binding.updateTitleEditText, binding.updateTitleCharacterCount, MAX_TITLE_LENGTH)
        setupTextWatcher(binding.updateContentEditText, binding.updateContentCharacterCount, MAX_CONTENT_LENGTH)

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val updatedTask = Task(taskId, newTitle, newContent)

            db.updateTask(updatedTask)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupTextWatcher(editText: EditText, countTextView: TextView, maxLength: Int) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    val currentLength = it.length
                    countTextView.text = "$currentLength/$maxLength"
                }
            }
        })
    }

    companion object {
        private const val MAX_TITLE_LENGTH = 60
        private const val MAX_CONTENT_LENGTH = 200
    }
}
