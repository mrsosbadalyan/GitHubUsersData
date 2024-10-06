package com.example.githubusersdata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(
    private val users: List<GitHubUser>,
    private val onUserClick: (GitHubUser) -> Unit // Callback for item click
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.login.text = user.login
        holder.id.text = "ID: ${user.id}" // Display user ID
        // Load user avatar using Glide
        Glide.with(holder.itemView.context).load(user.avatar_url).into(holder.avatar)

        // Set click listener for each item
        holder.itemView.setOnClickListener {
            onUserClick(user) // Pass the clicked user object to the listener
        }
    }

    override fun getItemCount() = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val login: TextView = itemView.findViewById(R.id.textViewLogin)
        val id: TextView = itemView.findViewById(R.id.textViewId) // User ID TextView
        val avatar: ImageView = itemView.findViewById(R.id.imageViewAvatar)
    }
}
