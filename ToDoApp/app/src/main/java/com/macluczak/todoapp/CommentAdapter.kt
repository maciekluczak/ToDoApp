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
import com.macluczak.todoapp.databinding.CommentHolderBinding
import com.macluczak.todoapp.databinding.ItemHolderBinding
import com.macluczak.todoapp.databinding.PersonalHolderBinding
import com.macluczak.todoapp.databinding.PostHolderBinding


class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: CommentHolderBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var comments: List<Comment>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = comments.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CommentHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val comment = comments[position]


            name.text = comment.name
            body.text = comment.body
            id.text = "ID: ${comment.id}"
            email.text = comment.email

        }


    }
}
