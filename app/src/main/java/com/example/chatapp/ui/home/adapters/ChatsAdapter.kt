package com.example.chatapp.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.data.model.User
import com.example.chatapp.databinding.ChatItemBinding

class ChatsAdapter(
    private val usersList: List<User>
) :
    RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ChatItemBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.txtUsername.text = item.displayName
            Glide.with(context).load("https://ps.w.org/simple-user-avatar/assets/icon-128x128.png?rev=2413146").into(binding.imgUser)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(usersList[position])
    }

    override fun getItemCount(): Int = usersList.size

}