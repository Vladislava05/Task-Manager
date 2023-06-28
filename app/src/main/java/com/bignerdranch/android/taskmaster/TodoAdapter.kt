package com.bignerdranch.android.taskmaster



import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView



import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.taskmaster.databinding.ItemTodoBinding



class TodoAdapter(
    private var todos: MutableList<Todo>, private var dones: MutableList<Todo>
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
              val tvTodoDate = findViewById(R.id.tvTodoDate) as TextView
              tvTodoTitle.text = curTodo.title
              tvTodoDate.text=curTodo.date

              val cbDone = findViewById(R.id.cbDone) as CheckBox
              cbDone.isChecked = curTodo.isChecked
              toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
              cbDone.setOnCheckedChangeListener { _, isChecked ->
                  toggleStrikeThrough(tvTodoTitle, isChecked)
                  curTodo.isChecked = !curTodo.isChecked


              }


              cbDone.setOnClickListener(View.OnClickListener{
                  todos.removeAll{todo->
                          todo.isChecked

                      }
                  dones.add(curTodo)

                      notifyDataSetChanged()

              })



        }
    }

    override fun getItemCount(): Int {
          return todos.size
    }



}