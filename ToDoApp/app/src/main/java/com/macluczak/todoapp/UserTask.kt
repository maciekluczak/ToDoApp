package com.macluczak.todoapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.macluczak.todoapp.databinding.ActivityPersonalDataBinding
import com.macluczak.todoapp.databinding.ActivityUserPostsBinding
import com.macluczak.todoapp.databinding.ActivityUserTaskBinding
import retrofit2.HttpException
import java.io.IOException

class UserTask : AppCompatActivity() {

    private lateinit var binding: ActivityUserTaskBinding
    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_posts)

        binding = ActivityUserTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        val id = intent.extras?.getString("id")

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getTodos()
            } catch(e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null) {
                val request = response.body()!!.filter { todo -> todo.userId == id?.toIntOrNull() }
                todoAdapter.todos = request
            } else {
                Log.e(ContentValues.TAG, "Response not successful")
            }

        }
    }
    private fun setupRecyclerView() = binding.userView.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@UserTask)

    }
}