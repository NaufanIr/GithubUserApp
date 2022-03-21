package com.example.githubuserapp.user_detail_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.ActivityUserDetailsBinding
import com.example.githubuserapp.user_detail_screen.tabs.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    private lateinit var binding: ActivityUserDetailsBinding
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(UserDetailViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Loading..."

        showLayout()
    }

    fun showLayout(){
        binding.itemUserDetails.progressBar.visibility = View.VISIBLE

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            viewModel.setData(username)
        }else{
            binding.itemUserDetails.progressBar.visibility = View.VISIBLE
        }

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager = binding.viewPager
        val tabs = binding.tabs
        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        viewModel.getData().observe(this, {
            if (it != null) {
                val item = binding.itemUserDetails

                item.apply {
                    supportActionBar?.title = it.login.lowercase()

                    Glide.with(this@UserDetailActivity)
                        .load(it.avatarUrl)
                        .circleCrop()
                        .into(circleImageView)

                    tvName.text = it.name?.uppercase() ?: "-"
                    tvFollowersCount.text = it.followers.toString()
                    tvRepositoriesCount.text = it.publicRepos.toString()
                    tvFollowingCount.text = it.following.toString()
                    tvCompany.text = it.company ?: "-"
                    tvLoc.text = it.location ?: "-"

                    binding.itemUserDetails.progressBar.visibility = View.INVISIBLE
                }
            }
        })
    }
}