package com.bignerdranch.android.taskmaster

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment

import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.taskmaster.Constants.TASK_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_DATE_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_DESCR_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.TaskListFragment.Companion.TASK_DETAIL_URI
import com.bignerdranch.android.taskmaster.databinding.ItemTodoBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TodoAdapter(
    private var todos: MutableList<Todo>,
    private val viewModel: TodoViewModel
):RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    class TodoViewHolder( ItemTodoBinding: ItemTodoBinding): RecyclerView.ViewHolder(ItemTodoBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val ItemTodoBinding = ItemTodoBinding.inflate(layoutInflater, parent, false)

        return TodoViewHolder(
            return TodoViewHolder(ItemTodoBinding)
        )
    }
    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked:Boolean){
        if(isChecked){
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        }else{
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
          val curTodo = todos[position]
          holder.itemView.apply{
              val tvTodoTitle = findViewById(R.id.tvTodoTitle) as TextView
              tvTodoTitle.text = curTodo.title
              val cbDone = findViewById(R.id.cbDone) as CheckBox
              val deleteBtn = findViewById(R.id.deleteBtn) as Button
              cbDone.isChecked = curTodo.isChecked
              toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
              cbDone.setOnCheckedChangeListener { _, isChecked ->
                  toggleStrikeThrough(tvTodoTitle, isChecked)
                  curTodo.isChecked = !curTodo.isChecked
              }
              cbDone.setOnClickListener(View.OnClickListener{
                  GlobalScope.launch {
                      viewModel.update(curTodo)
                      viewModel.delete(curTodo)
                  }
                      todos.removeAll { todo ->
                          todo.isChecked
                      }
                      notifyDataSetChanged()
              })

              deleteBtn.setOnClickListener{
                  GlobalScope.launch {
                      viewModel.delete(curTodo)
                  }
                  notifyDataSetChanged()
              }

              tvTodoTitle.setOnClickListener {
                  val request = NavDeepLinkRequest.Builder
                      .fromUri("${TASK_DETAIL_URI}?${TASK_BUNDLE_KEY}=${curTodo.title}&${TASK_DATE_BUNDLE_KEY}=${curTodo.date}&${TASK_DESCR_BUNDLE_KEY}=${curTodo.description}".toUri())
                      .build()
                  findNavController().navigate(request)

              }
        }
    }

    override fun getItemCount(): Int {
          return todos.size
    }

}