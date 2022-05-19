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


class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PostHolderBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var posts: List<Post>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = posts.size



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PostHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val post = posts[position]

            holder.itemView.setOnClickListener{
                val intent = Intent(holder.itemView.context, PostComments::class.java)
                intent.putExtra("post_id", post.id.toString())
                holder.itemView.getContext().startActivity(intent);
            }

            id.text = "ID: ${post.id}"
            name.text =post.title
            body.text = post.body


        }


    }
}
