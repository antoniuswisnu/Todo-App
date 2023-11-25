package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.databinding.ActivityTaskDetailBinding
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailTaskViewModel
    private lateinit var binding: ActivityTaskDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO 11 : Show detail task and implement delete action
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this)
        )[DetailTaskViewModel::class.java]

        val taskId: Int?
        if (intent.hasExtra(TASK_ID)) {
            taskId = intent.getIntExtra(TASK_ID, 0)
            viewModel.setTaskId(taskId)
            viewModel.task.observe(this) {
                if (it != null) updateUI(it) else finish()
            }
        }
    }

    private fun updateUI(task: Task) {
        binding.apply {
            detailEdTitle.setText(task.title)
            detailEdDescription.setText(task.description)
            detailEdDueDate.setText(DateConverter.convertMillisToString(task.dueDateMillis))
            btnDeleteTask.setOnClickListener {
                viewModel.deleteTask()
                finish()
            }
        }
    }
}
