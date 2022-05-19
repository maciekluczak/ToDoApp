package com.macluczak.todoapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.macluczak.todoapp.databinding.ActivityPersonalDataBinding
import com.macluczak.todoapp.databinding.ActivityPostCommentsBinding
import com.macluczak.todoapp.databinding.ActivityUserPostsBinding
import com.macluczak.todoapp.databinding.ActivityUserTaskBinding
import retrofit2.HttpException
import java.io.IOException

class PostComments : AppCompatActivity() {

    private lateinit var binding: ActivityPostCommentsBinding
    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_posts)

        binding = ActivityPostCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        val post_id = intent.extras?.getString("post_id")

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getComments()
            } catch(e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null) {
                val request = response.body()!!.filter { comment -> comment.postId == post_id?.toIntOrNull() }
                commentAdapter.comments = request
            } else {
                Log.e(ContentValues.TAG, "Response not successful")
            }

        }
    }
    private fun setupRecyclerView() = binding.userView.apply {
        commentAdapter = CommentAdapter()
        adapter = commentAdapter
        layoutManager = LinearLayoutManager(this@PostComments)

    }
}