package com.macluczak.todoapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.macluczak.todoapp.databinding.ActivityMainBinding
import com.macluczak.todoapp.databinding.ActivityPersonalDataBinding
import retrofit2.HttpException
import java.io.IOException

class Personal_Data : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalDataBinding
    private lateinit var personalAdapter: PersonalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_personal_data)
        binding = ActivityPersonalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        val id = intent.extras?.getString("id")
        val postBtn = findViewById<Button>(R.id.posts)
        val taskBtn = findViewById<Button>(R.id.Tasks)

        postBtn.setOnClickListener {
                val intent = Intent(this, UserPosts::class.java)
                intent.putExtra("id", id)
                startActivity(intent);
            }

        taskBtn.setOnClickListener {

            val intent = Intent(this, UserTask::class.java)
            intent.putExtra("id", id)
            startActivity(intent)

        }






        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getUsers()
            } catch(e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null) {
                val request = response.body()!!.filter { user -> user.id == id?.toIntOrNull() }
                personalAdapter.users = request
            } else {
                Log.e(ContentValues.TAG, "Response not successful")
            }

        }

    }
    private fun setupRecyclerView() = binding.userView.apply {
        personalAdapter = PersonalAdapter()
        adapter = personalAdapter
        layoutManager = LinearLayoutManager(this@Personal_Data)

    }
}