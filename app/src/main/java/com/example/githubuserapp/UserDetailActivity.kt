package com.example.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubuserapp.databinding.ActivityUserDetailsBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<DataUsers>("DATA") as DataUsers
        val item = binding.itemUserDetails

        item.apply {
            circleImageView.setImageResource(data.avatar)
            tvName.text = data.name.uppercase()
            tvUsername.text = data.username
            tvFollowersCount.text = data.followers
            tvRepositoriesCount.text = data.repository
            tvFollowingCount.text = data.following
            tvCompany.text = data.company
            tvLoc.text = data.location
        }
    }
}