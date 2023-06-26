package com.bignerdranch.android.taskmaster

import android.view.LayoutInflater

import android.view.ViewGroup

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.taskmaster.databinding.ItemDoneBinding



class DoneAdapter(private var dones: MutableList<Todo>): RecyclerView.Adapter<DoneAdapter.DoneViewHolder>() {
    class DoneViewHolder(ItemDoneBinding: ItemDoneBinding) :
        RecyclerView.ViewHolder(ItemDoneBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoneAdapter.DoneViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val ItemDoneBinding = ItemDoneBinding.inflate(layoutInflater, parent, false)

        return DoneAdapter.DoneViewHolder(
            return DoneAdapter.DoneViewHolder(ItemDoneBinding)
        )
    }

    override fun onBindViewHolder(holder: DoneAdapter.DoneViewHolder, position: Int) {
        holder.itemView.apply {

            val tvTodoDone = findViewById(R.id.tvDoneTitle) as TextView


        }


    }

    override fun getItemCount(): Int {
        return dones.size
    }
}