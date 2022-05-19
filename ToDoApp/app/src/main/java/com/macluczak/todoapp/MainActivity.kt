package com.macluczak.todoapp

import android.content.ClipData
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.macluczak.todoapp.databinding.ActivityMainBinding
import com.macluczak.todoapp.databinding.ItemHolderBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var simpleAdapter: SimpleAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()






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
//                val request = response.body()!!.filter { user -> user.id == 5 }
                simpleAdapter.users = response.body()!!
            } else {
                Log.e(ContentValues.TAG, "Response not successful")
            }

        }

    }
    private fun setupRecyclerView() = binding.userView.apply {
        simpleAdapter = SimpleAdapter()
        adapter = simpleAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)

    }


}