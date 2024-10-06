package com.example.githubusersdata

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        // Retrieve the username from the intent
        val username = intent.getStringExtra("username")
        val apiService = ApiClient.retrofit.create(GitHubApiService::class.java)

        // Find the back arrow ImageView
        val displayUserList: ImageView = findViewById(R.id.displayUserList)

        // Set onClick listener for the back arrow
        displayUserList.setOnClickListener {
            finish() // This will finish the current activity and return to the previous one
        }

        username?.let {
            // Fetch the user details from the API
            apiService.getUserDetails(it).enqueue(object : Callback<GitHubUser> {
                override fun onResponse(call: Call<GitHubUser>, response: Response<GitHubUser>) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        user?.let { displayUserDetails(it) } // Display the fetched user details
                    } else {
                        Log.e("UserDetailsActivity", "Error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<GitHubUser>, t: Throwable) {
                    Log.e("UserDetailsActivity", "Failed to load user details", t)
                }
            })
        }
    }

    private fun displayUserDetails(user: GitHubUser) {
        // Use findViewById to get the references to the views
        val textViewName: TextView = findViewById(R.id.textViewName)
        val textViewLocation: TextView = findViewById(R.id.textViewLocation)
        val textViewFollowers: TextView = findViewById(R.id.textViewFollowers)
        val textViewFollowing: TextView = findViewById(R.id.textViewFollowing)
        val textViewBio: TextView = findViewById(R.id.textViewBio)
        val textViewRepos: TextView = findViewById(R.id.textViewRepos)
        val textViewGists: TextView = findViewById(R.id.textViewGists)
        val textViewUpdatedAt: TextView = findViewById(R.id.textViewUpdatedAt)
        val imageViewAvatar: ImageView = findViewById(R.id.imageViewAvatar)

        textViewName.text = user.name ?: "No Name"
        textViewLocation.text = user.location ?: "No Location"
        textViewFollowers.text = "${user.followers ?: 0} Followers"
        textViewFollowing.text = "${user.following ?: 0} Following"
        textViewBio.text = user.bio ?: "No Bio"
        textViewRepos.text = "${user.public_repos ?: 0}"
        textViewGists.text = "${user.public_gists ?: 0}"
        textViewUpdatedAt.text = "${user.updated_at ?: "Never"}"

        Glide.with(this)
            .load(user.avatar_url)
            .transform(CircleCrop())
            .into(imageViewAvatar)
    }
}
