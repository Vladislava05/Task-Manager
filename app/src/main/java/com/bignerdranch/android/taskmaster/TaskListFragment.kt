package com.bignerdranch.android.taskmaster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.taskmaster.databinding.FragmentTaskListBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TaskListFragment : Fragment(R.layout.fragment_task_list) {
    private lateinit var todoAdapter: TodoAdapter
    private var _binding: FragmentTaskListBinding? = null
    private lateinit var todoDao: TodoDao
    private lateinit var viewModel: TodoViewModel
    private val binding: FragmentTaskListBinding
        get() = checkNotNull(_binding)
    private var todos: MutableList<Todo> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appDatabase = AppDatabase.getInstance(this.requireActivity())
        todoDao = appDatabase.todoDao()
        var repository = TaskRepository(todoDao)
        viewModel = ViewModelProvider(this, TodoViewModelFactory(repository)).get(TodoViewModel::class.java)
        viewModel.getAllTodos().observe(this, Observer { todoList ->
            todos.clear()
            todos.addAll(todoList)
            todoAdapter.notifyDataSetChanged()

        })

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoAdapter = TodoAdapter(todos,viewModel)
        binding.rvTodoItems.adapter = todoAdapter
        binding.rvTodoItems.layoutManager = LinearLayoutManager(this.activity)
        binding.btnAddTodo.setOnClickListener {
            NewTaskSheet().show(requireActivity().supportFragmentManager, "NewTaskTag")
        }
        requireActivity().supportFragmentManager.setFragmentResultListener(
            "requestKey",
            this
        ) { requestKey, bundle ->
            val newTodo = bundle.getSerializable("bundleKey") as Todo
            GlobalScope.launch {
                viewModel.insert(newTodo)
            }
            todoAdapter.notifyDataSetChanged()
        }
        binding.rvTodoItems.addItemDecoration(
            DividerItemDecoration(
                binding.rvTodoItems.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.btnFilter.setOnClickListener {
            todos.sortBy{it.title}
            todoAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}