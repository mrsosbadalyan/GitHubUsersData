package com.example.githubusersdata

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Fetch data from the API
        val apiService = ApiClient.retrofit.create(GitHubApiService::class.java)
        apiService.getUsers().enqueue(object : Callback<List<GitHubUser>> {
            override fun onResponse(call: Call<List<GitHubUser>>, response: Response<List<GitHubUser>>) {
                if (response.isSuccessful) {
                    val users = response.body() ?: emptyList()
                    // Set the adapter with the list of users
                    recyclerView.adapter = UserAdapter(users) { selectedUser ->
                        // On user click, navigate to the details page
                        val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)
                        intent.putExtra("username", selectedUser.login) // Pass username to UserDetailsActivity
                        startActivity(intent)
                    }
                } else {
                    Log.e("MainActivity", "API Response Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<GitHubUser>>, t: Throwable) {
                Log.e("MainActivity", "Failed to fetch users", t)
            }
        })
    }
}
