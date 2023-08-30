package com.bignerdranch.android.taskmaster

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController


import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.taskmaster.Constants.TASK_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_DATE_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_DESCR_BUNDLE_KEY
import com.bignerdranch.android.taskmaster.Constants.TASK_ID
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
                  val alertDialogBuilder = AlertDialog.Builder(this.context)
                  alertDialogBuilder.setMessage("Do you want to delete this task?")
                  alertDialogBuilder.setPositiveButton("Yes"){
                          dialogInterface, which ->
                      Toast.makeText(context,"Delete",Toast.LENGTH_LONG).show()
                      GlobalScope.launch {
                          viewModel.delete(curTodo)
                      }
                  }
                  alertDialogBuilder.setNegativeButton("No"){
                          dialogInterface, which ->
                      Toast.makeText(context,"Cancelled",Toast.LENGTH_LONG).show()
                  }
                  val alertDialog: AlertDialog = alertDialogBuilder.create()
                  alertDialog.setCancelable(false)
                  alertDialog.show()
                  notifyDataSetChanged()
              }

              tvTodoTitle.setOnClickListener {
                  val request = NavDeepLinkRequest.Builder
                      .fromUri("${TASK_DETAIL_URI}?${TASK_ID}=${curTodo.id}&${TASK_BUNDLE_KEY}=${curTodo.title}&${TASK_DESCR_BUNDLE_KEY}=${curTodo.description}&${TASK_DATE_BUNDLE_KEY}=${curTodo.date}".toUri())
                      .build()
                  findNavController().navigate(request)
              }
        }
    }

    override fun getItemCount(): Int {
          return todos.size
    }


    companion object{
        const val TASK_DETAIL_URI = "app://taskmaster.taskDetailFragment"
        const val TASK_LIST_URI ="app://taskmaster.taskListFragment"
        const val TASK_EDIT_URI="app://taskmaster.taskEditFragment"
        const val REGISTER_FRAGMENT_URI="app://taskmaster.registerFragment"
        const val LOGIN_FRAGMENT_URI="app://taskmaster.loginFragment"
    }

}