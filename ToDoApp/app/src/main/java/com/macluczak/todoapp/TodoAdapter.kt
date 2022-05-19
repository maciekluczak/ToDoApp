package com.macluczak.todoapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.macluczak.todoapp.databinding.ItemHolderBinding
import com.macluczak.todoapp.databinding.PersonalHolderBinding
import com.macluczak.todoapp.databinding.PostHolderBinding
import com.macluczak.todoapp.databinding.TaskHolderBinding


class TodoAdapter : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: TaskHolderBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var todos: List<Todo>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = todos.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TaskHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val todo = todos[position]

//            holder.itemView.setOnClickListener{
//                val intent = Intent(holder.itemView.context, Personal_Data::class.java)
//                holder.itemView.getContext().startActivity(intent);
//            }

            id.text = "ID: ${todo.id}"
            name.text =todo.title
            completed.text = "Completed: ${todo.completed}"


        }


    }
}
