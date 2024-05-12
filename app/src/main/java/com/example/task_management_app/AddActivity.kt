package com.example.task_management_app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.task_management_app.databinding.ActivityAddBinding
import com.example.task_management_app.Task
import com.example.task_management_app.TaskDatabaseHelper
import android.widget.Toast


class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var db : TaskDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TaskDatabaseHelper(this)

        setupTextWatcher(binding.titleEditText, binding.titleCharacterCount, MAX_TITLE_LENGTH)
        setupTextWatcher(binding.contentEditText, binding.contentCharacterCount, MAX_CONTENT_LENGTH)

        binding.saveButton.setOnClickListener{
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val task = Task(0, title, content)
            db.insertTask(task)
            finish()
            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()
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
