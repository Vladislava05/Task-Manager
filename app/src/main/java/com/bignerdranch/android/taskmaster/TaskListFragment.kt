package com.bignerdranch.android.taskmaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.taskmaster.databinding.FragmentTaskListBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TaskListFragment : Fragment(R.layout.fragment_task_list) {
    private lateinit var todoAdapter: TodoAdapter
    private var _binding: FragmentTaskListBinding? = null
    private lateinit var todoDao: TodoDao
    private val binding: FragmentTaskListBinding
        get() = checkNotNull(_binding)
    private var todos: MutableList<Todo> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appDatabase = AppDatabase.getInstance(this.requireActivity())
        todoDao = appDatabase.todoDao()
        val task = Todo(title = "Заголовок", description = "Описание", date="1/12/23", isChecked = false)
        GlobalScope.launch {
            todoDao.insert(task)
        }
        todoDao.getAllTasks().observe(this, Observer { tasks ->
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
        todoAdapter = TodoAdapter(todos)
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
            todos.add(newTodo)
            todoAdapter.notifyDataSetChanged()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}