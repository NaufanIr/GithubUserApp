package com.example.githubuserapp.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.UsersData
import com.example.githubuserapp.databinding.ItemUserBinding

class AdapterUserList(private val userList: List<UsersData>)
    : RecyclerView.Adapter<AdapterUserList.ListViewHolder>() {


    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.binding.apply {
            tvName.text = userList[position].login.lowercase()
            tvUsername.text = StringBuilder("id :").append(userList[position].id)
            Glide.with(holder.itemView.context)
                .load(userList[position].avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.user)
                .error(R.drawable.user)
                .into(imgUser)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(userList[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int = userList.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UsersData)
    }

}