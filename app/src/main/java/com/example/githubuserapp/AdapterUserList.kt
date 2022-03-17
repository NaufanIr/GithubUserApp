package com.example.githubuserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.databinding.ItemUserBinding

class AdapterUserList(
    private val userList: ArrayList<DataUsers>,
    ) : RecyclerView.Adapter<AdapterUserList.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val name = userList[position].name
        val loc = userList[position].location
        val company = userList[position].company

        val city = loc.substring(0, loc.indexOf(','))
        val description = "$name • $city • $company"

        holder.binding.imgUser.setImageResource(userList[position].avatar)
        holder.binding.tvUsername.text = userList[position].username
        holder.binding.tvDesc.text = description

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(userList[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int = userList.size


    interface OnItemClickCallback {
        fun onItemClicked(data: DataUsers)
    }

}