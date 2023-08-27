package com.bignerdranch.android.taskmaster

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.taskmaster.TodoAdapter.Companion.TASK_DETAIL_URI
import com.bignerdranch.android.taskmaster.TodoAdapter.Companion.TASK_LIST_URI
import com.bignerdranch.android.taskmaster.databinding.FragmentTaskEditBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskEditFragment : Fragment() {
    private var _binding: FragmentTaskEditBinding? = null
    private val binding: FragmentTaskEditBinding
        get() = checkNotNull(_binding)
    private lateinit var viewModel: TodoViewModel
    private lateinit var todoDao: TodoDao
    private var todos: MutableList<Todo> = mutableListOf()
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appDatabase = AppDatabase.getInstance(this.requireActivity())
        todoDao = appDatabase.todoDao()
        var repository = TaskRepository(todoDao)
        viewModel = ViewModelProvider(this, TodoViewModelFactory(repository)).get(TodoViewModel::class.java)
        repository.allTasks.observe(this, Observer { todoList ->
            todos.clear()
            todos.addAll(todoList)
            todoAdapter.notifyDataSetChanged()

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoAdapter = TodoAdapter(todos,viewModel)
        val id = arguments?.getInt("taskId")
        val title = arguments?.getString(Constants.TASK_BUNDLE_KEY)
        val date = arguments?.getString(Constants.TASK_DATE_BUNDLE_KEY)
        val descr = arguments?.getString(Constants.TASK_DESCR_BUNDLE_KEY)
        binding.title.setText(title)
        binding.date.setText(date)
        binding.description.setText(descr)

        binding.btnSave.setOnClickListener {
            val new_title = binding.title.text.toString()
            val new_date = binding.date.text.toString()
            val new_descr = binding.description.text.toString()
            val updatedTodo = Todo(id!!.toInt(), new_title, new_descr, new_date)
            GlobalScope.launch{
                viewModel.update(updatedTodo)
                Log.d("myApp", updatedTodo.toString())
            }
            val request = NavDeepLinkRequest.Builder
                .fromUri(TASK_LIST_URI.toUri())
                .build()
            findNavController().navigate(request)
        }
    }

}