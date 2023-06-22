package com.bignerdranch.android.taskmaster


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle



import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.taskmaster.databinding.ActivityMainBinding



class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter:TodoAdapter
    private lateinit var binding: ActivityMainBinding
    private var todos: MutableList<Todo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)




        todoAdapter = TodoAdapter(todos)
        binding.rvTodoItems.adapter = todoAdapter
        binding.rvTodoItems.layoutManager = LinearLayoutManager(this)
        binding.btnAddTodo.setOnClickListener{
            NewTaskSheet().show(supportFragmentManager,"NewTaskTag")
            }
        supportFragmentManager.setFragmentResultListener("requestKey",this){ requestKey,bundle ->
            val newTodo=bundle.getSerializable("bundleKey") as Todo
            todos.add(newTodo)
            todoAdapter.notifyDataSetChanged()
        }

    }



}